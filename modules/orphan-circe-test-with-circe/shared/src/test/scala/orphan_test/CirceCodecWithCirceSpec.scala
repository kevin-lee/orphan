package orphan_test

import hedgehog.*
import hedgehog.runner.*
import io.circe.literal.*
import io.circe.parser.*
import io.circe.syntax.*
import orphan_instance.OrphanCirceInstances.MyBoxForCodec

/** @author Kevin Lee
  * @since 2025-08-10
  */
object CirceCodecWithCirceSpec extends Properties {

  override def tests: List[Test] = List(
    property("round-trip test CirceCodec", testCirceCodec),
    property("test CirceCodec encoding", testCirceCodecEncoding),
    property("test CirceCodec decoding", testCirceCodecDecoding),
  )

  def testCirceCodec: Property = for {
    id       <- Gen.int(Range.linear(0, Int.MaxValue)).log("id")
    name     <- Gen.string(Gen.alpha, Range.linear(0, 10)).log("name")
    isActive <- Gen.boolean.log("isActive")
    myBox    <- Gen.constant(MyBoxForCodec(id, name, isActive)).log("myBox")
  } yield {
    val expectedJson =
      json"""{
               "id":$id,
               "name":$name,
               "isActive":$isActive
             }"""

    val actualJson     = myBox.asJson
    val encodingResult = actualJson ==== expectedJson

    val expected       = myBox
    val decodingResult = decode[MyBoxForCodec](actualJson.noSpaces) ==== Right(expected)

    Result.all(
      List(
        encodingResult,
        decodingResult,
      )
    )

  }

  def testCirceCodecEncoding: Property = for {
    id       <- Gen.int(Range.linear(0, Int.MaxValue)).log("id")
    name     <- Gen.string(Gen.alpha, Range.linear(0, 10)).log("name")
    isActive <- Gen.boolean.log("isActive")
    myBox    <- Gen.constant(MyBoxForCodec(id, name, isActive)).log("myBox")
  } yield {
    val expected =
      json"""{
               "id":$id,
               "name":$name,
               "isActive":$isActive
             }"""

    val actual = myBox.asJson
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testCirceCodecDecoding: Property = for {
    id            <- Gen.int(Range.linear(0, Int.MaxValue)).log("id")
    name          <- Gen.string(Gen.alpha, Range.linear(0, 10)).log("name")
    isActive      <- Gen.boolean.log("isActive")
    myBoxForCodec <- Gen.constant(MyBoxForCodec(id, name, isActive)).log("myBoxForCodec")
  } yield {
    val jsonInput =
      json"""{
               "id":$id,
               "name":$name,
               "isActive":$isActive
             }"""

    val expected = myBoxForCodec
    decode[MyBoxForCodec](jsonInput.noSpaces) ==== Right(expected)

  }

}
