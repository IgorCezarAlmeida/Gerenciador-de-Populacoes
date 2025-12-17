/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadordepopulacoes.util;

/**
 *
 * @author Jonat
 */

import java.awt.Desktop;
import java.io.File;
import java.io.IOException; // Adicionado para cobrir a exceção File/Desktop
import java.sql.Connection;
import java.sql.SQLException; // Adicionado para cobrir a exceção Connection
import java.util.Map;

import net.sf.jasperreports.engine.JRException; // Adicionado para cobrir as exceções do Jasper
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Alexis
 */
public class RelatorioUtil {

    public static void abrirPDF(String caminho, Map<String, Object> parametros, Connection con) throws IOException, JRException, SQLException {
        try {
            JasperReport relatorio = (JasperReport) JRLoader.loadObjectFromFile(caminho);

            JasperPrint print = JasperFillManager.fillReport(relatorio, parametros, con);

            File pdfTemp = File.createTempFile("relatorio_", ".pdf");
            pdfTemp.deleteOnExit();

            JasperExportManager.exportReportToPdfFile(print, pdfTemp.getAbsolutePath());

            Desktop.getDesktop().open(pdfTemp);
            
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
