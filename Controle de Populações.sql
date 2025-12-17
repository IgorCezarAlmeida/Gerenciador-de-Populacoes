DROP FUNCTION IF EXISTS excluir_pais(p_id INT);
DROP FUNCTION IF EXISTS excluir_estado(e_id INT);

DROP VIEW IF EXISTS vw_grupos_com_estados;
DROP VIEW IF EXISTS vw_estados_detalhe;
DROP VIEW IF EXISTS vw_paises_simples;
DROP VIEW IF EXISTS vw_usuarios_detalhe;

REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA public FROM role_admin;
REVOKE ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public FROM role_admin;

REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA public FROM role_leitura;
REVOKE ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public FROM role_leitura;

DROP ROLE IF EXISTS role_admin; 
DROP ROLE IF EXISTS role_leitura; 

DROP TABLE IF EXISTS grupos_populacionais CASCADE;
DROP TABLE IF EXISTS estado CASCADE;
DROP TABLE IF EXISTS paises CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS empresas CASCADE;

CREATE TABLE empresas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome_usuario VARCHAR(50) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE,
    nivel_acesso VARCHAR(20) NOT NULL CHECK (nivel_acesso IN ('ADMIN', 'GESTOR', 'LEITURA')), 
    id_empresa INTEGER NOT NULL REFERENCES empresas(id) ON DELETE RESTRICT,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE paises (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE 
);

CREATE TABLE estados (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    id_pais INTEGER NOT NULL REFERENCES paises(id) ON DELETE CASCADE
);

CREATE TABLE grupos_populacionais (
    id SERIAL PRIMARY KEY,
    tamanho INTEGER NOT NULL CHECK (tamanho >= 0),
    tipo VARCHAR(100) NOT NULL,
    nacionalidade VARCHAR(100),
    religiao VARCHAR(100),
    id_estado INTEGER NOT NULL REFERENCES estados(id) ON DELETE CASCADE
);


INSERT INTO empresas (nome) VALUES 
('Governanca Global'), 
('Instituto de Pesquisa X'), 
('ONG de População Local');

INSERT INTO usuarios (nome_usuario, senha_hash, email, id_empresa, nivel_acesso) VALUES
('111', '111', '111', (SELECT id FROM empresas WHERE nome = 'Governanca Global'), 'ADMIN'),
('222', '222', '222', (SELECT id FROM empresas WHERE nome = 'Instituto de Pesquisa X'), 'GESTOR'),
('333', '333', '333', (SELECT id FROM empresas WHERE nome = 'ONG de População Local'), 'LEITURA');

INSERT INTO paises (nome) VALUES 
('Brasil'), ('Itália'), ('Estados Unidos');

INSERT INTO estados (nome, id_pais) VALUES
('São Paulo', (SELECT id FROM paises WHERE nome = 'Brasil')),
('Rio de Janeiro', (SELECT id FROM paises WHERE nome = 'Brasil')),
('Lombardia', (SELECT id FROM paises WHERE nome = 'Itália')),
('California', (SELECT id FROM paises WHERE nome = 'Estados Unidos'));

INSERT INTO grupos_populacionais (tamanho, tipo, nacionalidade, religiao, id_estado) VALUES
(300, 'motoristas', 'alemao', 'ateus', (SELECT id FROM estados WHERE nome = 'Lombardia')),
(250, 'músicos', 'brasileira', 'católica', (SELECT id FROM estados WHERE nome = 'São Paulo')),
(40, 'dançarinos', 'brasileira', 'sem religião', (SELECT id FROM estados WHERE nome = 'Rio de Janeiro')),
(500, 'tecnologia', 'estadunidense', 'sem religião', (SELECT id FROM estados WHERE nome = 'California'));


CREATE OR REPLACE VIEW vw_grupos_com_estados AS
SELECT g.id, g.tamanho, g.tipo, g.nacionalidade, g.religiao, e.nome AS estado
FROM grupos_populacionais g JOIN estados e ON g.id_estado = e.id;

CREATE OR REPLACE VIEW vw_estados_detalhe AS
SELECT e.id AS id_estado, e.nome AS estado_nome, e.id_pais, p.nome AS pais_nome
FROM estados e JOIN paises p ON e.id_pais = p.id ORDER BY e.nome;

CREATE OR REPLACE VIEW vw_paises_simples AS
SELECT id, nome FROM paises ORDER BY nome;

CREATE OR REPLACE VIEW vw_usuarios_detalhe AS
SELECT 
    u.id,
    u.nome_usuario,
    u.email,
    e.nome AS nome_empresa,
    u.nivel_acesso,
    u.ativo
FROM usuarios u
JOIN empresas e ON u.id_empresa = e.id
ORDER BY u.nome_usuario;

CREATE OR REPLACE FUNCTION excluir_pais(p_id INT)
RETURNS VOID AS $$
BEGIN
    DELETE FROM paises WHERE id = p_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION excluir_estado(e_id INT)
RETURNS VOID AS $$
BEGIN
    DELETE FROM estados WHERE id = e_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE sp_editar_grupo_populacional (novo_tamanho INT,novo_tipo VARCHAR, 
nova_nacionalidade VARCHAR,nova_religiao VARCHAR, id_estado INT,id_p INT)  
	AS $$  
	BEGIN
	IF novo_tamanho < 0 THEN 
	RAISE EXCEPTION 'tamanho menor que 0'; 
	END IF;
	
	UPDATE grupos_populacionais 
	SET 
	tamanho = novo_tamanho,
	tipo = novo_tipo,
	nacionalidade = nova_nacionalidade,
	religiao = nova_religiao
	WHERE 
	id = id_p; 
	END; $$ LANGUAGE plpgsql;


CREATE OR REPLACE PROCEDURE sp_excluir_grupo_populacional (id_p INT)  
	AS $$  
	BEGIN

	DELETE FROM grupos_populacionais 
	WHERE id_p = id; 

	END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION fn_validar_update_grupo()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.tamanho < 0 THEN
        RAISE EXCEPTION 'Tamanho não pode ser negativo!';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tg_validar_update_grupo
BEFORE UPDATE ON grupos_populacionais
FOR EACH ROW
EXECUTE FUNCTION fn_validar_update_grupo();

CREATE ROLE role_admin; 
CREATE ROLE role_leitura; 

GRANT SELECT ON ALL TABLES IN SCHEMA public TO role_leitura;
GRANT SELECT ON ALL SEQUENCES IN SCHEMA public TO role_leitura;

REVOKE DELETE, INSERT, UPDATE ON grupos_populacionais FROM role_leitura;
REVOKE DELETE, INSERT, UPDATE ON estados FROM role_leitura;
REVOKE DELETE, INSERT, UPDATE ON paises FROM role_leitura;
REVOKE DELETE, INSERT, UPDATE ON empresas FROM role_leitura;
REVOKE DELETE, INSERT, UPDATE ON usuarios FROM role_leitura;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO role_admin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO role_admin;