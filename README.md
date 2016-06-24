```

openssl genrsa -out server.key 2048
openssl req -new -key server.key -out server.csr
openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
cat server.crt server.key | tee server.pem

haproxy -f haproxy.cfg
```


The running backend service:

```
http://localhost:8080
```

Access with HAProxy with http

http://localhost:8081

Access with HAProxy with https:

```
https://localhost:8443/

```

```
curl --insecure https://localhost:8443
```

Create client certificate:

```
openssl genrsa -out client.key 2048
openssl req -new -key client.key -out client.csr
openssl x509 -req -days 365 -in client.csr -CA haproxy.crt -CAkey haproxy.key -out client.crt

# Convert Client Key to PKCS
openssl pkcs12 -export -clcerts -in client.crt -inkey client.key -out client.p12
```
