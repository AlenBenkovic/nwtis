java -jar dist\vjezba_05_1.jar NWTiS_matnovak.txt

keytool -keyalg RSA -genkey -dname "CN=Matija Novak, OU=NWTiS O=FOI, L=Varazdin, ST=Croatia, C=HR" -alias matnovak -keypass 123456 -keystore matnovak.jks -storepass nwtis2013 

keytool -list -keystore matnovak.jks -storepass nwtis2013 

jarsigner -keystore matnovak.jks -storepass nwtis2013 -keypass 123456 -signedjar dist\vjezba_05_1_sign.jar dist\vjezba_05_1.jar matnovak

jarsigner -verify -verbose -certs dist\vjezba_05_1_sign.jar

keytool -export -keystore matnovak.jks -storepass nwtis2013 -alias  matnovak -file matnovak.cer

java -jar dist\vjezba_05_1_sign.jar NWTiS_matnovak.txt

java -Djava.security.manager -jar dist\vjezba_05_1_sign.jar NWTiS_matnovak.txt

policytool -file matnovak.policy

java -Djava.security.manager -Djava.security.policy=matnovak.policy -jar dist\vjezba_05_1_sign.jar NWTiS_matnovak.txt

policytool -file matnovak_signed.policy

java -Djava.security.manager -Djava.security.policy=matnovak_signed.policy -jar dist\vjezba_05_1_sign.jar NWTiS_matnovak.txt

jarsigner -keystore matnovak.jks -storepass nwtis2013 -keypass 123456 -signedjar lib\vjezba_03_2_sign.jar lib\vjezba_03_2.jar matnovak