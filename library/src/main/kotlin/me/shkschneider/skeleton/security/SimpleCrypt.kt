package me.shkschneider.skeleton.security

class SimpleCrypt {

    private val ALGORITHM = "XOR"
    private val _key: CharArray

    constructor(key: String) {
        _key = key.toCharArray()
    }

    fun algorithm(): String {
        return ALGORITHM
    }

    fun key(): String {
        return String(_key)
    }

    fun encrypt(string: String): String {
        val chars = string.toCharArray()
        val encrypted = CharArray(chars.size)
        for (i in chars.indices) {
            encrypted[i] = (chars[i].toInt() xor _key[i % _key.size].toInt()).toChar()
        }
        return Base64Helper.encode(String(encrypted).toByteArray())
    }

    fun decrypt(string: String): String? {
        val encrypted = Base64Helper.decode(string)
        val decrypted = CharArray(encrypted.size)
        for (i in encrypted.indices) {
            decrypted[i] = (encrypted[i].toInt() xor _key[i % _key.size].toInt()).toChar()
        }
        return String(decrypted)
    }

}
