### Publishing [SBT](http://www.scala-sbt.org) projects from [travis-ci](https://travis-ci.org/r) to [OSS](https://oss.sonatype.org/)

#### Step 0. Create Sonatype account

This basically includes [signing up](https://issues.sonatype.org/secure/Signup!default.jspa) and creating a *New Project* JIRA ticket. After this step, your JIRA username and password is authorized to publish artifacts to OSS.
In this document, the OSS username and OSS password are henceforth referred as *SONATYPE_USERNAME* and *SONATYPE_PASSWORD*.

#### Step 1. Generate PGP key and submitting the key to a keyserver

1. PGP key generation: The [sbt-pgp](http://www.scala-sbt.org/sbt-pgp/) plugin specifies two methods for key generation. I was only successful with the gpg utility. In OSX, this can be installed with the command
` brew install gpg`. After installing the utility, the key can be generated using the command `gpg --gen-key`. This will ask for *RealName*, *Email*, *Comment*, and *Passphrase*, the Passphrase given will be referred henceforth as *PASSPHRASE*.

2. Sending the key to keyserver: Use the command, `gpg send-key <EMAIL_GIVEN_IN_LAST_COMMAND> hkp://pool.sks-keyservers.net`. The command will finish execution instantaneously, if not make sure you have your firewall disabled. 
Instead of using the <EMAIL_GIVEN_IN_LAST_COMMAND>, you can use the RSA KEY ID, this can be fetched using the command on any encrypted files generated (e.g.,` gpg --verify target/scala-2.10/biglibrary_2.10-0.0.3.pom.asc`).



#### Step 2. Add the Sonatype credentials and *PASSPHRASE* to `.travis.yml` 

1. Install travis utility to your machine. In OSX, use the command `sudo gem install travis`. 
2. From the *ROOT* folder of the project (where the `.travis.yml` is located) issue the following commands:
  * `travis encrypt PGP_PASSPHRASE=<PASSPHRASE> --add` 
  * `travis encrypt SONATYPE_USERNANE=<SONATYPE_USERNAME> --add` 
  * `travis encrypt SONATYPE_PASSWORD=<SONATYPE_PASSWORD> --add` 

These commands will add entries like below in your `.travis.yml` file.

```YAML
env:
  global:
  - secure: H3HnKxnSWssUp75fV/aPUUxkrJM8zqq9icoN3gijR9qKg1+fKaYHH3NoSQOO5BavRpEE7QIcw/PmQnDl1z2Y7dyespQZTvzhVHfS6utkaZjyQXE7pxj4FmGZR8lSTq32TXs2Y4u7YD1vQo4elBLMWKYdjJagXWAFStcQ9sM6RA/8LC5BJpeWguvzJgY6RmovMvEgrW2EidtwgAntLdoENXCCck6dw1YMwhNoUiQSKaNhqqW8+IWlH7lLKNeRGnZVOmE45nzY1EtvIy7erYbtmx814+KiGMdcHxA2RXikNBc/k7L/1PvO6ujUnEn+sGHWUmLH5AGQMiezOp9inQUHqIDlTVTamJdXFgnVsIyrtp2aNheZ8UmitEz2xAdGhhRVgI/bUTONs+kL6nuRwKb46alfHVUshOz6DNR30OeBEOcvc5h0i3jr4R0Se9M+hnqCfllu2R2w5v2t8+qOyQg/d6YXP8+VQTz3z35ErlAA5MgKiE/ZCdpWcbakplGDpzEPw8p9MkE8HaFzzy4d1tTkXi5fBfsBYvq22DxKYd+66eB5SL+Wl6bsnXliZ7uDnPZTzOyxH/+UdXIY9LSGVh+eOpN5HQ+F74WVZdhdUw15F8eXJjElcGTUfGOCTezquhAKCKJDAG5v9hNNFU7h07RaGYBxg9tjkll/SfyhdTzvTh8=
  - secure: w3qaPrrkadkcCWu8UGSkvHLPQ4MQKyaK9YDNMCxrdSetfAjQek3+N6W9EWLW2nHgoZKWoaxJGDxa9z6bLxd24a5/0r9oePnx88I2nQ4URt0C5M5iwrCwlUs7INBUGBqLU/5JQveaUZZKxb5O5xf4BHqE93V6tPo1cimkSqezf26v37o/VBFvaI2UAEFaB2Kfud/6pwxS3lEC2DcsYgBZNuKtMsaMnc6mxNCfIjLwAVhwJAZ1Ezxbuk1oG6jTROLcfyG2qD7612vdK97/Kpr6ltwGsiiw1gNIc/5X16on8n2IYaaulKsKvm9ahBRqjpW/eRCkyPcksiSG4WGY23uNI1cn3nQ70Gz3+qYJ6lGw1GXIn2X25KmeTaap0n+FxZlrCY+/ayGjt/Y4Mh9CrSA+grE/BQj3V8r6WwSzerVbhkKgeF16Oea3j4kbRVs7q2tLNwSkuhQIb84TPCHojRCty80wGC9nItVIC1ylPM4+EFH3YPmkRFnPbsaxiSmrwRrCI1V+KrW1ats02i1tto+wHFcHljN0TlgPbTgruy0uhBkPrtcfzDDYVM0G2TSiSsgrsNLJbQyuLcUZ5PsTMNfRN3V0iLMRdAwbQ6NcBwIlWS3z9zJLDuWTd92TSu/E8e/BB86+EPOg1NUun6EoSUXhtxrnIUFphr/Ldc5eDnJIs2g=
  - secure: NSohemkgg7rU7KbmTtB59POgJeCj+0yCQO07nm0T33NiRuV4dwsa9rmbQOJIiARIAtDUr690OES61R6WwoTji0IZhbrzlckBhxvEAyWgc4QbDwetqPhv4tB28anJjJx6+3bmmu6iZn0zniBkwAkNWoXT6xdpzSe4p6GF5y2KuG+lZd7klk7CIHYNQWyMHWhFjHaAotIPFuR+QwFd5u3F+ZRotaRd99A8d5RtVx3S7/rEFB5tI/BXW17z5w/co3ziWYkLjPxVPJjxIIjjaPMLbfYG0mvfRLHFa0kHclPNmJRhXz9TF1+iaPKVCJS4n3Gx+vczvVOMz9bKLNv7D3k0MGs3qICxAz9FGF+LyPSJdAO8Iy9JrIW1aTzI0uPeXp6hBIb//pDLsTae/Lla83GShfFeNFmFvaBpQTfeN/M2vIomigku2U78ko2czkX4EpYdvvcntgY2ZM9g0cl1Wc41SIgtg8Iz/rchvUB/r8gLygyhD3HNmaMfcsqA9rquLnzwnupZGULecG4icpUKym2lcGSR4/8ICyB4JWzmpIvhi9pEgzGQ8xeP5YotnYj48vZuaygwSqvuBr4YujvGIgvhStGcz4+K7U82tOVzp+/czP3XxvNH1CEHoje61DsemVxewu8+W8ddawx7BTaF/F40gvmjHgoVMEbWB2gwcGgqTqk=
```
 
#### Step 3. Encrypt the PGP keys and add them to the repository

1. There are two files that need to be added to the repository along with the source code for enabling the encryption from travis: 1) `secring.gpg` and 2) `pubring.gpg`. In OSX, the files are found in `~/.gnupg/`. You can copy
that files to the *ROOT* folder of the project (add .gitignore entries to ignore will pushing to the repository). 
2. After copying the two files: 1) `secring.gpg` and 2) `pubring.gpg`, issue these commands:
  * `openssl aes-256-cbc -in secring.gpg -out secring.gpg.enc -pass pass:<PGP_PASSPHRASE>`
  * `openssl aes-256-cbc -in pubring.gpg -out pubring.gpg.enc -pass pass:<PGP_PASSPHRASE>`

#### Step 4. Configure the `.travis.yml` to read the encrypted files and decrypt.

1. Add the following section to the `.travis.yml` file.

```YAML
before_install:
- openssl aes-256-cbc -pass pass:$PGP_PASSPHRASE -in secring.gpg.enc -out secring.gpg -d
- openssl aes-256-cbc -pass pass:$PGP_PASSPHRASE -in pubring.gpg.enc -out pubring.gpg -d
```

#### Step 5. Configure the `Build.scala/build.sbt` to read the credentials from environment variables available in travis.

1. Add the following lines to the `build.sbt/Build.scala` file.

```SCALA
import com.typesafe.sbt.SbtPgp.autoImportImpl._
 credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", System.getenv().get("SONATYPE_USERNAME"), System.getenv().get("SONATYPE_PASSWORD"))
 pgpPassphrase := Some( System.getenv().get("PGP_PASSPHRASE").toCharArray),
 pgpSecretRing := file("secring.gpg"),
 pgpPublicRing := file("pubring.gpg"),
```
 My code is in `Build.scala` but I don't find any reason for the code not to work on `build.sbt`. 

##### Credits
1. [Shipping from GitHub to Maven Central and S3, using Travis-CI](https://www.theguardian.com/info/developer-blog/2014/sep/16/shipping-from-github-to-maven-central-and-s3-using-travis-ci)
