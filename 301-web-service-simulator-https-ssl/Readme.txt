1. Generating keystore and certificates command.
======================================================
    1. Generate keystore
        keytool -genkey -alias localserver -keystore localkeystore.jks -dname "CN=localhost, O=localhost, C=IN" -storepass passw0rd -keypass passw0rd -storetype jks  -validity 1000 -keyalg RSA
    
    2. Export certificate from server for the client
    	keytool -export -alias localserver -file localserver.cer -keystore localkeystore.jks	
    
    3. Import certificate in a truststore for client
    	keytool -import -trustcacerts -alias localserver -file localserver.cer -keystore localtruststore.ts
    	
    4. List all available certificates in keystore
		keytool -list -v  -keystore .keystore  