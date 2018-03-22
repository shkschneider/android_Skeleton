package me.shkschneider.skeleton.security

class SimpleCrypt : ICrypt<String> {

    private val ALGORITHM = "XOR"

    private val key: CharArray

    constructor(key: String) : super(key) {
        this.key = key.toCharArray()
    }

    override fun algorithm(): String {
        return ALGORITHM
    }

    override fun key(): String {
        return String(key)
    }

    override fun encrypt(src: String): String? {
        val chars = src.toCharArray()
        val encrypted = CharArray(chars.size)
        for (i in chars.indices) {
            encrypted[i] = (chars[i].toInt() xor key[i % key.size].toInt()).toChar()
        }
        return Base64Helper.encode(String(encrypted).toByteArray())
    }

    override fun decrypt(src: String): String? {
        val encrypted = Base64Helper.decode(src)
        val decrypted = CharArray(encrypted.size)
        for (i in encrypted.indices) {
            decrypted[i] = (encrypted[i].toInt() xor key[i % key.size].toInt()).toChar()
        }
        return String(decrypted)
    }

}
