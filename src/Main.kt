fun main() {
    // Задание 1
    val tests = listOf(10L, 61L, 120L, 300L, 660L, 1260L, 1500L, 7200L, 100000L, 400000L)

    for (s in tests) {
        val text = when {
            s <= 60 -> "только что"
            s <= 3600 -> {
                val m = (s / 60).toInt()

                if (m == 1) "1 минуту назад"
                else if (m % 100 in 11..14) "$m минут назад"
                else if (m % 10 == 1) "$m минуту назад"
                else if (m % 10 in 2..4) "$m минуты назад"
                else "$m минут назад"
            }

            s <= 86400 -> {
                val h = (s / 3600).toInt()

                if (h == 1) "1 час назад"
                else if (h % 100 in 11..14) "$h часов назад"
                else if (h % 10 in 2..4) "$h часа назад"
                else "$h часов назад"
            }

            s <= 172800 -> "вчера"
            s <= 259200 -> "позавчера"
            else -> "давно"
        }

        println("был(а) $text")
    }

    fun calculateCommission(
        cardType: String = "VK Pay",
        previousTransfers: Int = 0,
        transfer: Int
    ): Int {
        require(previousTransfers >= 0 && transfer > 0) {
            "Суммы должны быть положительными"
        }

        return when (cardType.trim().lowercase()) {
            "vk pay" -> 0

            "mastercard", "т-банк" -> {
                val monthlyLimit = 75_000
                val total = previousTransfers + transfer

                if (total <= monthlyLimit) {
                    0
                } else {
                    val amountOverLimit = total - maxOf(previousTransfers, monthlyLimit)
                    (amountOverLimit * 0.006 + 20).toInt()
                }
            }

            "visa", "мир" -> maxOf((transfer * 0.0075).toInt(), 35)

            else -> error("Неизвестный тип карты или счёта: $cardType")
        }
    }

    print("Введите тип карты/счёта (VK Pay, MasterCard, Visa, Мир): ")
    val cardType = readlnOrNull()?.trim().takeUnless { it.isNullOrBlank() } ?: "VK Pay"

    print("Введите сумму предыдущих переводов за месяц: ")
    val previousTransfers = readlnOrNull()?.toIntOrNull() ?: 0

    print("Введите сумму перевода: ")
    val transfer = readlnOrNull()?.toIntOrNull()
        ?: error("Некорректная сумма перевода")

    val commission = calculateCommission(cardType, previousTransfers, transfer)
    println("Комиссия: $commission рублей")
}