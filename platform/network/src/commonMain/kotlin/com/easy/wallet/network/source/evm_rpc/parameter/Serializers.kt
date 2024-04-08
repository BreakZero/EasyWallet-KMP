package com.easy.wallet.network.source.evm_rpc.parameter

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*


internal object IntListParameterSerializer : KSerializer<Parameter.IntListParameter> {
    override fun deserialize(decoder: Decoder): Parameter.IntListParameter {
        val items = decoder.decodeSerializableValue(ListSerializer(Int.serializer()))
        return Parameter.IntListParameter(
            items = items
        )
    }

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("IntListParameter") {
            this.element<List<Int>>("items")
        }

    override fun serialize(encoder: Encoder, value: Parameter.IntListParameter) {
        encoder.encodeSerializableValue(ListSerializer(Int.serializer()), value.items)
    }
}

internal object StringParameterSerializer: KSerializer<Parameter.StringParameter> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("StringParameter") {
            this.element<String>("content")
        }

    override fun deserialize(decoder: Decoder): Parameter.StringParameter {
        val content = decoder.decodeString()
        return Parameter.StringParameter(content = content)
    }

    override fun serialize(encoder: Encoder, value: Parameter.StringParameter) {
        encoder.encodeString(value.content)
    }
}

internal object RpcRequestBodySerializer : KSerializer<RpcRequestBody> {
    override fun deserialize(decoder: Decoder): RpcRequestBody {
        return decoder.decodeStructure(descriptor) {
            var rpc: String? = null
            var method: String? = null
            var params: List<Parameter> = emptyList()
            var id: Int? = null
            loop@ while (true) {
                when (decodeElementIndex(descriptor)) {
                    CompositeDecoder.DECODE_DONE -> break@loop

                    0 -> rpc = decodeStringElement(descriptor, 0)
                    1 -> method = decodeStringElement(descriptor, 1)
                    2 -> params = decodeSerializableElement(
                        descriptor, 2, ListSerializer(
                            ParameterSerialize
                        ))
                    3 -> id = decodeIntElement(descriptor, 3)
                }
            }
            RpcRequestBody(
                jsonrpc = rpc.orEmpty(),
                method = method.orEmpty(),
                params = params,
                id = id ?: 1
            )
        }
    }

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("RpcRequestBody") {
            this.element<String>("jsonrpc", isOptional = false)
            this.element<String>("method", isOptional = false)
            this.element<List<Parameter>>("params", isOptional = false)
            this.element<Int>("id", isOptional = false)
        }

    override fun serialize(encoder: Encoder, value: RpcRequestBody) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.jsonrpc)
            encodeStringElement(descriptor, 1, value.method)
            encodeSerializableElement(descriptor, 2, ListSerializer(Parameter.serializer()), value.params)
            encodeIntElement(descriptor, 3, value.id)
        }
    }
}

internal object ParameterSerialize : JsonContentPolymorphicSerializer<Parameter>(Parameter::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Parameter> {
        // override the condition for your point
        return when(element) {
            is JsonPrimitive -> Parameter.StringParameter.serializer()
            is JsonArray -> Parameter.IntListParameter.serializer()
            is JsonObject -> Parameter.CallParameter.serializer()
        }
    }
}
