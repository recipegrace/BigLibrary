### Publishing [SBT](http://www.scala-sbt.org) projects from [travis-ci](https://travis-ci.org/r) to [OSS](https://oss.sonatype.org/)

#### Step 0. Create Sonatype account

This basically includes [signing up](https://issues.sonatype.org/secure/Signup!default.jspa) and creating a *New Project* JIRA ticket.  


#### Step 1. Generate PGP key and submitting the key to a keyserver

1. PGP key generation: The [sbt-pgp](http://www.scala-sbt.org/sbt-pgp/) plugin specifies two methods for key generation. I was only successful with the gpg utility. In OSX, this can be installed with the command
` brew install gpg`. After installing the utility, the key can be generated using the command `gpg --gen-key`. This is ask for a *passphrase* and make sure you make a note of it. The passphrase given will be referred henceforth as *PASSPHRASE*

2.  

