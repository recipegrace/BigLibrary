### Publishing [SBT](http://www.scala-sbt.org) projects from [travis-ci](https://travis-ci.org/r) to [OSS](https://oss.sonatype.org/)

#### Step 0. Create Sonatype account

This basically includes [signing up](https://issues.sonatype.org/secure/Signup!default.jspa) and creating a *New Project* JIRA ticket. After this step, your JIRA username and password is authorized to publish artifacts to OSS.
In this document, the OSS username and OSS password are henceforth referred as *SONATYPE_USERNAME* and *SONATYPE_PASSWORD*.

#### Step 1. Generate PGP key and submitting the key to a keyserver

1. PGP key generation: The [sbt-pgp](http://www.scala-sbt.org/sbt-pgp/) plugin specifies two methods for key generation. I was only successful with the gpg utility. In OSX, this can be installed with the command
` brew install gpg`. After installing the utility, the key can be generated using the command `gpg --gen-key`. This will ask for *RealName*, *Email*, *Comment*, and *Passphrase*, the Passphrase given will be referred henceforth as *PASSPHRASE*

2. Sending the key to keyserver: Use the command, `gpg send-key <EMAIL_GIVEN_IN_LAST_COMMAND> hkp://pool.sks-keyservers.net. The command will finish execution instantaneously, if not make sure you have your firewall disabled. 
Instead of using the <EMAIL_GIVEN_IN_LAST_COMMAND>, you can use the RSA KEY ID, this can be fetched using the command on any encrypted files generated (e.g.,` gpg --verify target/scala-2.10/biglibrary_2.10-0.0.3.pom.asc`).



 

