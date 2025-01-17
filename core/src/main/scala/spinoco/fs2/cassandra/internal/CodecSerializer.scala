package spinoco.fs2.cassandra.internal

import com.datastax.oss.driver.api.core.ProtocolVersion
import scodec.Attempt
import scodec.bits.{BitVector, ByteVector}
import spinoco.fs2.cassandra.CType

import java.nio.ByteBuffer

object CodecSerializer {
  implicit class CodecSerializeSyntax[V](val self: CType[V]) extends AnyVal {
    def serialize(v: V, protocolVersion: ProtocolVersion): Attempt[ByteBuffer] = {
      self
        .cqlCodec(protocolVersion)
        .encode(v)
        .map(_.toByteBuffer)
    }

    def deserialize(bb: ByteBuffer, protocolVersion: ProtocolVersion): Either[Throwable, V] = {
      self
        .cqlCodec(protocolVersion)
        .decode(BitVector(bb))
        .toEither
        .left.map(e => new Throwable(e.message))
        .right.map(_.value)
    }

    def deserialize(bv: ByteVector, protocolVersion: ProtocolVersion): Either[Throwable, V] = deserialize(bv.toByteBuffer, protocolVersion)
  }
}
