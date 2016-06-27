useGpg := true

// the following lines are being ignored as long as we use the GPG binary

val pgpPass = ("" + System.getProperty("PGP_PASSPHRASE")).toCharArray

pgpPassphrase := {
  if (!isSnapshot.value)
    Some(pgpPass)
  else
    Some(Array())
}
