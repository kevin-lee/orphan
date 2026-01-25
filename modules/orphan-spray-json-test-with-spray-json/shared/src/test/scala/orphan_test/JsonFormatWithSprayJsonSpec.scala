package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanSprayJsonInstances.MyBoxForJsonFormat
import spray.json.*

/** @author Kevin Lee
  * @since 2026-01-25
  */
object JsonFormatWithSprayJsonSpec extends Properties {

  override def tests: List[Test] = List(
    property("round-trip test JsonFormat", testJsonFormat),
    property("test JsonFormat writing", testJsonFormatWriting),
    property("test JsonFormat reading", testJsonFormatReading),
  )

  def testJsonFormat: Property = for {
    id                 <- Gen.int(Range.linear(0, Int.MaxValue)).log("id")
    name               <- Gen.string(Gen.alpha, Range.linear(0, 10)).log("name")
    isActive           <- Gen.boolean.log("isActive")
    myBoxForJsonFormat <- Gen.constant(MyBoxForJsonFormat(id, name, isActive)).log("myBoxForJsonFormat")
  } yield {

    val expectedJson = JsObject(
      "id"       -> JsNumber(id),
      "name"     -> JsString(name),
      "isActive" -> JsBoolean(isActive),
    )

    val actualJson = myBoxForJsonFormat.toJson

    val encodingResult          = actualJson ==== expectedJson
    val encodedJsonStringResult = actualJson.compactPrint ==== expectedJson.compactPrint

    val expected       = myBoxForJsonFormat
    val decodingResult = actualJson.convertTo[MyBoxForJsonFormat] ==== expected

    Result.all(
      List(
        encodingResult.log("encoded Json"),
        encodedJsonStringResult.log("encoded Json String"),
        decodingResult,
      )
    )

  }

  def testJsonFormatWriting: Property = for {
    id                 <- Gen.int(Range.linear(0, Int.MaxValue)).log("id")
    name               <- Gen.string(Gen.alpha, Range.linear(0, 10)).log("name")
    isActive           <- Gen.boolean.log("isActive")
    myBoxForJsonFormat <- Gen.constant(MyBoxForJsonFormat(id, name, isActive)).log("myBoxForJsonFormat")
  } yield {

    val expectedJson = JsObject(
      "id"       -> JsNumber(id),
      "name"     -> JsString(name),
      "isActive" -> JsBoolean(isActive),
    )

    val actual = myBoxForJsonFormat.toJson

    Result.all(
      List(
        (actual ==== expectedJson).log("Encoded Json comparison failed"),
        (actual.compactPrint ==== expectedJson.compactPrint).log("Encoded Json String comparison failed"),
      )
    )
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testJsonFormatReading: Property = for {
    id                 <- Gen.int(Range.linear(0, Int.MaxValue)).log("id")
    name               <- Gen.string(Gen.alpha, Range.linear(0, 10)).log("name")
    isActive           <- Gen.boolean.log("isActive")
    myBoxForJsonFormat <- Gen.constant(MyBoxForJsonFormat(id, name, isActive)).log("myBoxForJsonFormat")
  } yield {

    val jsonInput  = JsObject(
      "id"       -> JsNumber(id),
      "name"     -> JsString(name),
      "isActive" -> JsBoolean(isActive),
    )
    val jsonString = jsonInput.compactPrint

    val expected = myBoxForJsonFormat
    jsonString.parseJson.convertTo[MyBoxForJsonFormat] ==== expected

  }

}
