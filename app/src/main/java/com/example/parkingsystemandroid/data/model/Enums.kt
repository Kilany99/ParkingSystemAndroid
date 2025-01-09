package com.example.parkingsystemandroid.data.model

class Enums {
    enum class SpotStatus {
        AVAILABLE,
        OCCUPIED,
        MAINTENANCE,
        OUTOFSERVICE
    }
    enum class SpotType {
        STANDARD,
        HANDICAPPED,
        VIP,
        ELECTRIC
    }

    enum class SessionStatus {
        RESERVED,
        ACTIVE,
        COMPLETED,
        CANCELLED
    }
    enum class PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REFUNDED
    }
    enum class PaymentMethod {
        CASH,
        CREDITCARD,
        DEBITCARD,
        MOBILEPAY
    }

}