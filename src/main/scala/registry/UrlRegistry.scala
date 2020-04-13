package registry
import parser.Parser
import search.{FinderBuilder, JsonSearchPropertyBuilder}
import structure.{JsonObjectValue, JsonValue, Property, Value}

/**
 * An implementation of the REgistry trait.
 */
class UrlRegistry extends Registry {

  /**
   * Returns the parser that corresponds to a specific url.
   * @param url The url that will be parsed or its prefix.
   * @return An Option[Parser] containing the Parser only if one was found for the provided id.
   */
  override def findParser(url: String): Option[Parser] = url match {
    // Implement method.
    case link if link.startsWith("http://www.lanacion.com.ar/") =>
      val result = new Parser() {
        override protected def createStructure(): Value = {
          val parser = FinderBuilder()
            .id("Schema_NewsArticle")
            .selectHTML()
          val structure = JsonObjectValue(parser)
          // We are parsing the json many times.
          val articleId = JsonSearchPropertyBuilder().find("url", parser)
          val imageId = JsonSearchPropertyBuilder().field("image").find("url", parser)
          val publisherId = JsonSearchPropertyBuilder().field("publisher").find("url", parser)
          val logoId = JsonSearchPropertyBuilder().field("publisher").field("logo").find("url", parser)
          // Should we add the id?
          structure
            .addPropertyAtIndex(Property("@id", JsonValue(articleId)), 0)
            .addPropertyAtIndex(Property("@id", JsonValue(imageId)), 0, List("image"))
            .addPropertyAtIndex(Property("@id", JsonValue(publisherId)), 0, List("publisher"))
            .addPropertyAtIndex(Property("@id", JsonValue(logoId)), 0, List("publisher", "logo"))
        }
      }
      Some(result)

    case link if link.startsWith("http://www.imdb.com/title/") =>
      val result = new Parser() {
        override protected def createStructure(): Value = {
          val parser = FinderBuilder()
            .attributeValue("type", "application/ld+json")
            .selectHTML()
          JsonObjectValue(parser)
        }
      }
      Some(result)

    case _ => None
  }
}
