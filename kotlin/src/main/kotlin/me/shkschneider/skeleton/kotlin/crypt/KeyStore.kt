package me.shkschneider.skeleton.kotlin.crypt

import java.security.PrivateKey
import java.security.PublicKey

data class KeyStore(
    val privateKey: PrivateKey,
    val publicKey: PublicKey
)
