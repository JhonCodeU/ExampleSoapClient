import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Jhon Arcila Castano
 */

public class SOAPClient {
    public static void main(String[] args) {
        try {
            // SOAP Endpoint URL
            String endpointUrl = "https://campus.udenar.edu.co:443/wsbo_est/wsrecaudoudenar.php";

            // Create SOAP XML Request
            String bodyXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                    "   <SOAP-ENV:Body>\n" +
                    "       <consultarRecaudo xmlns=\"http://presentacion.ws.recaudos.v2/\">\n" +
                    "           <xml></xml>\n" +
                    "       </consultarRecaudo>\n" +
                    "   </SOAP-ENV:Body>\n" +
                    "</SOAP-ENV:Envelope>";
            
            String cDataXml = "<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "           <consultar-recaudos-input>\n" +
                    "               <canal>99</canal>\n" +
                    "               <clave>0cc1d3nt3</clave>\n" +
                    "               <cod_banco>023</cod_banco>\n" +
                    "               <cod_producto>10</cod_producto>\n" +
                    "               <codigo_iac>7709998008403</codigo_iac>\n" +
                    "               <fecha_transaccion>20250203</fecha_transaccion>\n" +
                    "               <hora_transaccion>161957</hora_transaccion>\n" +
                    "               <jornada>1</jornada>\n" +
                    "               <nro_cuenta>039122379</nro_cuenta>\n" +
                    "               <nro_terminal>039-42</nro_terminal>\n" +
                    "               <oficina>001</oficina>\n" +
                    "               <operador>89410</operador>\n" +
                    "               <referencia1>1290683999</referencia1>\n" +
                    "               <tipo_consulta>FAC</tipo_consulta>\n" +
                    "               <tipo_registro>389</tipo_registro>\n" +
                    "               <usuario>boccidente</usuario>\n" +
                    "           </consultar-recaudos-input>\n" +
                    "           ]]>";
            
            String soapXml = bodyXml.replace("<xml></xml>", cDataXml);

            // Open Connection
            URL url = new URL(endpointUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "http://presentacion.ws.recaudos.v2/#consultarRecaudo");
            conn.setDoOutput(true);

            // Send Request
            OutputStream os = conn.getOutputStream();
            os.write(soapXml.getBytes());
            os.close();

            // Read Response
            java.io.InputStream is = conn.getInputStream();
            byte[] responseBytes = is.readAllBytes();
            String response = new String(responseBytes);
            
            //Parser the response
            XMLParser.parseXMLResponse(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}