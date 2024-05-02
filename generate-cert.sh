openssl genrsa -aes256 -passout pass:password -out CA-private-key.key 4096

openssl req -new -key CA-private-key.key -passin pass:password -subj "/CN=Certificate authority/" -out CA-certificate-signing-request.csr

openssl x509 -req -in CA-certificate-signing-request.csr -signkey CA-private-key.key -passin pass:password -days 7 -out CA-self-signed-certificate.pem

openssl genrsa -aes256 -passout pass:password -out Server-private-key.key 4096
openssl req -new -key Server-private-key.key -passin pass:password -subj "/CN=localhost/" -out Server-certificate-signing-request.csr

openssl genrsa -aes256 -passout pass:password -out Client-private-key.key 4096
openssl req -new -key Client-private-key.key -passin pass:password -subj "/CN=Client/" -out Client-certificate-signing-request.csr

openssl x509 -req -in Server-certificate-signing-request.csr -CA CA-self-signed-certificate.pem -CAkey CA-private-key.key -passin pass:password -CAcreateserial -days 7 -out Server-certificate.pem
openssl x509 -req -in Client-certificate-signing-request.csr -CA CA-self-signed-certificate.pem -CAkey CA-private-key.key -passin pass:password -days 7 -out Client-certificate.pem
