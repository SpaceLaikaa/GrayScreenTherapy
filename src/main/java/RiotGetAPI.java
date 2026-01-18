import java.net.http.HttpClient;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class RiotGetAPI{
    public static HttpClient createUnsafeClient(){
        try{
            TrustManager[] trustAllCerts = new TrustManager[]{ // To get riot's trust.
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                    }
            };
            javax.net.ssl.SSLContext sslContext = javax.net.ssl.SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            return HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();
        }catch (Exception e){
            throw new RuntimeException("Client creation failed: " + e.getMessage());
        }
    }
}