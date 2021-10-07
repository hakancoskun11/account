package com.folksdev.account.dto

import javax.validation.constraints.NotBlank
import java.math.BigDecimal
import javax.validation.constraints.Min

class CreateAccountRequest (
        @field:NotBlank
        val customerId : String,
        @field:Min(0)
        val initialCredit : BigDecimal
        )