package com.easy.wallet.shared

import com.trustwallet.core.AnySigner
import com.trustwallet.core.CoinType
import com.trustwallet.core.PrivateKey
import com.trustwallet.core.ethereum.SigningInput
import com.trustwallet.core.ethereum.SigningOutput
import com.trustwallet.core.ethereum.Transaction
import com.trustwallet.core.sign
import okio.ByteString
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Run it in ios platform or add expect and actual class to load TrustWalletCore for Android
 */
class SigningUnitTest {
  @Test
  fun test() {
    val signingInput = SigningInput(
      private_key = ByteString.of(
        *PrivateKey("0x4646464646464646464646464646464646464646464646464646464646464646".toHexByteArray()).data
      ),
      to_address = "0x3535353535353535353535353535353535353535",
      chain_id = "0x1".asHex(),
      nonce = "0x9".asHex(),
      gas_price = "0x04a817c800".asHex(),
      gas_limit = "0x5208".asHex(),
      transaction = Transaction(
        transfer = Transaction.Transfer(amount = "0x0de0b6b3a7640000".asHex())
      )
    )
    val output = AnySigner.sign(signingInput, CoinType.Ethereum, SigningOutput.ADAPTER)
    val encoded = "0x${output.encoded.hex()}"
    assertEquals(
      encoded,
      "0xf86c098504a817c800825208943535353535353535353535353535353535353535880de0b6b3a76400008025a028ef61340bd939bc2195fe537567866003e1a15d3c71ff63e1590620aa636276a067cbe9d8997f761aecb703304b3800ccf555c9f3dc64214b297fb1966a3b6d83"
    )
  }
}
