package structure

import fetcher.UrlFetcher
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.Json
import search.{FinderBuilder, SearchNode}
import response.{Response, SuccessfulResponse}
import scrapper.{Scrapper, UrlScrapper}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class StructureTest extends AnyFlatSpec with Matchers {

  "Values" should "be parsable" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><a id=\"link\" href=\"http://schema.org\"></a></body></html>")
    val parser = FinderBuilder().id("link").selectAttribute("href")
    val result = RootValue(ObjectValue(List(Property("name", StringValue("Tom")), Property("value", FetchedValue(parser)))))
    println(result.parse())
  }

  "Test" should "be able to fetch a real site" in {
    val response: Response[Document] = Await.result(new UrlFetcher().fetch("http://www.lanacion.com.ar/2351900", 10000), 5 seconds)
    response shouldBe a[SuccessfulResponse[_]]
    response match {
      case SuccessfulResponse(result) =>
        implicit val document: Document = result
        val parser = FinderBuilder().classes(List("titulo")).selectText()
        val res = RootValue(ObjectValue(List(Property("title", FetchedValue(parser)))))
        println(res.parse())
      case _ =>
    }
  }

  "Test" should "be able to find the json-ld" in {
    val response: Response[Document] = Await.result(new UrlFetcher().fetch("http://www.lanacion.com.ar/2351900", 10000), 5 seconds)
    response shouldBe a[SuccessfulResponse[_]]
    response match {
      case SuccessfulResponse(result) =>
        implicit val document: Document = result
        val parser = FinderBuilder()
          .id("Schema_NewsArticle")
          .selectHTML()
        val res = RootValue(FetchedValue(parser))
        val parsed = res.parse()
        println(parsed)
      case _ =>
    }
  }

  "Test" should "be able to build a DataStructure" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><a id=\"link\" href=\"http://www.lanacion.com.ar\"></a></body></html>")
    val parser = FinderBuilder()
      .id("link")
      .selectAttribute("href")
    val data = DataStructureBuilder(FetchedValue(parser), StringValue("NewsArticle"))
      .withContext(StringValue("http://schema.org"))
        .build()
    println(data.parse())
  }

  "Test" should "convert json-ld to DataStructure" in {
    val response: Response[Document] = Await.result(new UrlFetcher().fetch("http://www.lanacion.com.ar/2351900", 10000), 5 seconds)
    response shouldBe a[SuccessfulResponse[_]]
    response match {
      case SuccessfulResponse(result) =>
        implicit val document: Document = result
        val parser = FinderBuilder()
          .id("Schema_NewsArticle")
          .selectHTML()
        val json = parser.value()
        val res = ObjectValue().fromJson(json.get)
        println(res.depthParse())
        // println(Json.parse(res.parse()))
        // Json.parse(res.parse())
      case _ =>
    }
  }

  "Scrapper" should "scrap a site" in {
    val scrapper = new UrlScrapper()
    val result = Await.result(scrapper.scrap("http://www.imdb.com/title/tt0111161/"), 5 seconds)
    result match {
      case SuccessfulResponse(json) => println(json)
      case _ => println("Something went wrong")
    }
  }

  "Scrapper" should "be able to scrap different sites" in {
    val scrapper = new UrlScrapper()
    val result = Await.result(scrapper.scrap("http://www.lanacion.com.ar/2351900"), 5 seconds)
    result match {
      case SuccessfulResponse(json) => println(json)
      case _ => println("Something went wrong")
    }
  }

  "Scrapper" should "batch scrap many urls" in {
    val scrapper = new UrlScrapper()
    val urls: List[String] = List("http://www.imdb.com/title/tt0111161/", "http://www.imdb.com/title/tt0068646/", "http://www.imdb.com/title/tt0071562/", "http://www.imdb.com/title/tt0468569/", "http://www.imdb.com/title/tt0050083/", "http://www.imdb.com/title/tt0108052/", "http://www.imdb.com/title/tt0167260/", "http://www.imdb.com/title/tt0110912/", "http://www.imdb.com/title/tt0060196/", "http://www.imdb.com/title/tt0137523/", "http://www.imdb.com/title/tt0120737/", "http://www.imdb.com/title/tt0109830/", "http://www.imdb.com/title/tt0080684/", "http://www.imdb.com/title/tt1375666/", "http://www.imdb.com/title/tt0167261/", "http://www.imdb.com/title/tt0073486/", "http://www.imdb.com/title/tt0099685/", "http://www.imdb.com/title/tt0133093/", "http://www.imdb.com/title/tt0047478/", "http://www.imdb.com/title/tt0317248/", "http://www.imdb.com/title/tt0076759/", "http://www.imdb.com/title/tt0114369/", "http://www.imdb.com/title/tt0102926/", "http://www.imdb.com/title/tt0038650/", "http://www.imdb.com/title/tt0118799/", "http://www.imdb.com/title/tt0114814/", "http://www.imdb.com/title/tt0245429/", "http://www.imdb.com/title/tt0110413/", "http://www.imdb.com/title/tt0120815/", "http://www.imdb.com/title/tt0816692/", "http://www.imdb.com/title/tt0120689/", "http://www.imdb.com/title/tt0120586/", "http://www.imdb.com/title/tt0054215/", "http://www.imdb.com/title/tt0064116/", "http://www.imdb.com/title/tt0021749/", "http://www.imdb.com/title/tt0034583/", "http://www.imdb.com/title/tt1675434/", "http://www.imdb.com/title/tt0027977/", "http://www.imdb.com/title/tt0253474/", "http://www.imdb.com/title/tt0407887/", "http://www.imdb.com/title/tt0082971/", "http://www.imdb.com/title/tt0103064/", "http://www.imdb.com/title/tt0047396/", "http://www.imdb.com/title/tt0088763/", "http://www.imdb.com/title/tt2582802/", "http://www.imdb.com/title/tt0172495/", "http://www.imdb.com/title/tt0110357/", "http://www.imdb.com/title/tt0482571/", "http://www.imdb.com/title/tt0209144/", "http://www.imdb.com/title/tt0078788/", "http://www.imdb.com/title/tt0078748/", "http://www.imdb.com/title/tt0032553/", "http://www.imdb.com/title/tt0095765/", "http://www.imdb.com/title/tt0043014/", "http://www.imdb.com/title/tt0057012/", "http://www.imdb.com/title/tt0095327/", "http://www.imdb.com/title/tt0405094/", "http://www.imdb.com/title/tt0050825/", "http://www.imdb.com/title/tt2380307/", "http://www.imdb.com/title/tt0081505/", "http://www.imdb.com/title/tt1853728/", "http://www.imdb.com/title/tt0910970/", "http://www.imdb.com/title/tt0119698/", "http://www.imdb.com/title/tt0169547/", "http://www.imdb.com/title/tt1345836/", "http://www.imdb.com/title/tt0051201/", "http://www.imdb.com/title/tt0364569/", "http://www.imdb.com/title/tt0090605/", "http://www.imdb.com/title/tt0087843/", "http://www.imdb.com/title/tt0082096/", "http://www.imdb.com/title/tt0033467/", "http://www.imdb.com/title/tt0053125/", "http://www.imdb.com/title/tt0052357/", "http://www.imdb.com/title/tt0112573/", "http://www.imdb.com/title/tt0105236/", "http://www.imdb.com/title/tt0086190/", "http://www.imdb.com/title/tt5074352/", "http://www.imdb.com/title/tt0022100/", "http://www.imdb.com/title/tt5311514/", "http://www.imdb.com/title/tt0180093/", "http://www.imdb.com/title/tt0986264/", "http://www.imdb.com/title/tt0086879/", "http://www.imdb.com/title/tt0211915/", "http://www.imdb.com/title/tt0066921/", "http://www.imdb.com/title/tt0056172/", "http://www.imdb.com/title/tt0338013/", "http://www.imdb.com/title/tt0036775/", "http://www.imdb.com/title/tt0075314/", "http://www.imdb.com/title/tt0045152/", "http://www.imdb.com/title/tt0062622/", "http://www.imdb.com/title/tt1187043/", "http://www.imdb.com/title/tt0114709/", "http://www.imdb.com/title/tt0056592/", "http://www.imdb.com/title/tt0093058/", "http://www.imdb.com/title/tt0361748/", "http://www.imdb.com/title/tt0070735/", "http://www.imdb.com/title/tt0040522/", "http://www.imdb.com/title/tt0012349/", "http://www.imdb.com/title/tt0435761/", "http://www.imdb.com/title/tt0208092/", "http://www.imdb.com/title/tt0119217/", "http://www.imdb.com/title/tt2106476/", "http://www.imdb.com/title/tt0071853/", "http://www.imdb.com/title/tt0059578/", "http://www.imdb.com/title/tt0086250/", "http://www.imdb.com/title/tt0119488/", "http://www.imdb.com/title/tt0053604/", "http://www.imdb.com/title/tt0017136/", "http://www.imdb.com/title/tt1832382/", "http://www.imdb.com/title/tt0042876/", "http://www.imdb.com/title/tt0097576/", "http://www.imdb.com/title/tt1049413/", "http://www.imdb.com/title/tt0055630/", "http://www.imdb.com/title/tt0042192/", "http://www.imdb.com/title/tt0372784/", "http://www.imdb.com/title/tt0053291/", "http://www.imdb.com/title/tt0105695/", "http://www.imdb.com/title/tt0040897/", "http://www.imdb.com/title/tt0476735/", "http://www.imdb.com/title/tt0363163/", "http://www.imdb.com/title/tt0095016/", "http://www.imdb.com/title/tt0113277/", "http://www.imdb.com/title/tt0081398/", "http://www.imdb.com/title/tt0044741/", "http://www.imdb.com/title/tt5027774/", "http://www.imdb.com/title/tt0057115/", "http://www.imdb.com/title/tt0041959/", "http://www.imdb.com/title/tt0118849/", "http://www.imdb.com/title/tt0071315/", "http://www.imdb.com/title/tt0457430/", "http://www.imdb.com/title/tt1255953/", "http://www.imdb.com/title/tt0096283/", "http://www.imdb.com/title/tt0089881/", "http://www.imdb.com/title/tt0055031/", "http://www.imdb.com/title/tt1305806/", "http://www.imdb.com/title/tt0015864/", "http://www.imdb.com/title/tt0347149/", "http://www.imdb.com/title/tt0050212/", "http://www.imdb.com/title/tt0047296/", "http://www.imdb.com/title/tt2096673/", "http://www.imdb.com/title/tt0050976/", "http://www.imdb.com/title/tt0120735/", "http://www.imdb.com/title/tt0268978/", "http://www.imdb.com/title/tt0112641/", "http://www.imdb.com/title/tt3170832/", "http://www.imdb.com/title/tt0031679/", "http://www.imdb.com/title/tt0080678/", "http://www.imdb.com/title/tt0993846/", "http://www.imdb.com/title/tt0434409/", "http://www.imdb.com/title/tt0015324/", "http://www.imdb.com/title/tt0083658/", "http://www.imdb.com/title/tt0050986/", "http://www.imdb.com/title/tt1291584/", "http://www.imdb.com/title/tt0017925/", "http://www.imdb.com/title/tt0046912/", "http://www.imdb.com/title/tt0117951/", "http://www.imdb.com/title/tt0031381/", "http://www.imdb.com/title/tt1205489/", "http://www.imdb.com/title/tt0477348/", "http://www.imdb.com/title/tt0167404/", "http://www.imdb.com/title/tt0116282/", "http://www.imdb.com/title/tt0077416/", "http://www.imdb.com/title/tt0469494/", "http://www.imdb.com/title/tt0084787/", "http://www.imdb.com/title/tt0266543/", "http://www.imdb.com/title/tt0118715/", "http://www.imdb.com/title/tt0091251/", "http://www.imdb.com/title/tt0018455/", "http://www.imdb.com/title/tt0061512/", "http://www.imdb.com/title/tt0046438/", "http://www.imdb.com/title/tt0266697/", "http://www.imdb.com/title/tt0032976/", "http://www.imdb.com/title/tt0116231/", "http://www.imdb.com/title/tt1130884/", "http://www.imdb.com/title/tt2119532/", "http://www.imdb.com/title/tt0060107/", "http://www.imdb.com/title/tt0892769/", "http://www.imdb.com/title/tt0978762/", "http://www.imdb.com/title/tt2267998/", "http://www.imdb.com/title/tt1856101/", "http://www.imdb.com/title/tt0758758/", "http://www.imdb.com/title/tt0079470/", "http://www.imdb.com/title/tt3011894/", "http://www.imdb.com/title/tt0405508/", "http://www.imdb.com/title/tt0025316/", "http://www.imdb.com/title/tt0019254/", "http://www.imdb.com/title/tt0091763/", "http://www.imdb.com/title/tt0107207/", "http://www.imdb.com/title/tt0395169/", "http://www.imdb.com/title/tt0074958/", "http://www.imdb.com/title/tt0092005/", "http://www.imdb.com/title/tt2278388/", "http://www.imdb.com/title/tt0046268/", "http://www.imdb.com/title/tt0079944/", "http://www.imdb.com/title/tt0052618/", "http://www.imdb.com/title/tt0107290/", "http://www.imdb.com/title/tt1979320/", "http://www.imdb.com/title/tt0060827/", "http://www.imdb.com/title/tt0353969/", "http://www.imdb.com/title/tt2024544/", "http://www.imdb.com/title/tt0405159/", "http://www.imdb.com/title/tt0120382/", "http://www.imdb.com/title/tt0053198/", "http://www.imdb.com/title/tt1392190/", "http://www.imdb.com/title/tt1895587/", "http://www.imdb.com/title/tt3315342/", "http://www.imdb.com/title/tt0245712/", "http://www.imdb.com/title/tt0112471/", "http://www.imdb.com/title/tt1028532/", "http://www.imdb.com/title/tt0064115/", "http://www.imdb.com/title/tt3783958/", "http://www.imdb.com/title/tt0087544/", "http://www.imdb.com/title/tt0093779/", "http://www.imdb.com/title/tt1392214/", "http://www.imdb.com/title/tt1280558/", "http://www.imdb.com/title/tt0264464/", "http://www.imdb.com/title/tt0033870/", "http://www.imdb.com/title/tt1201607/", "http://www.imdb.com/title/tt0075148/", "http://www.imdb.com/title/tt0050783/", "http://www.imdb.com/title/tt0032551/", "http://www.imdb.com/title/tt0198781/", "http://www.imdb.com/title/tt0046911/", "http://www.imdb.com/title/tt0246578/", "http://www.imdb.com/title/tt0072684/", "http://www.imdb.com/title/tt0070510/", "http://www.imdb.com/title/tt0083987/", "http://www.imdb.com/title/tt0052311/", "http://www.imdb.com/title/tt0088247/", "http://www.imdb.com/title/tt0113247/", "http://www.imdb.com/title/tt0107048/", "http://www.imdb.com/title/tt0032138/", "http://www.imdb.com/title/tt0440963/", "http://www.imdb.com/title/tt0075686/", "http://www.imdb.com/title/tt0097165/", "http://www.imdb.com/title/tt0056801/", "http://www.imdb.com/title/tt0073195/", "http://www.imdb.com/title/tt0118694/", "http://www.imdb.com/title/tt1454029/", "http://www.imdb.com/title/tt0036868/", "http://www.imdb.com/title/tt0087884/", "http://www.imdb.com/title/tt0338564/", "http://www.imdb.com/title/tt0114746/", "http://www.imdb.com/title/tt0101414/", "http://www.imdb.com/title/tt0111495/", "http://www.imdb.com/title/tt0381681/", "http://www.imdb.com/title/tt2991224/", "http://www.imdb.com/title/tt4430212/", "http://www.imdb.com/title/tt0058946/", "http://www.imdb.com/title/tt0072890/")
    val result = Await.result(scrapper.batchScrap(urls), 500 seconds)
    result match {
      case SuccessfulResponse(json) =>
      case _ => println("Something went wrong")
    }
  }

  "Scrapper" should "be able to scrap even more sites" in {
    val scrapper = new UrlScrapper()
    val result = Await.result(scrapper.scrap("https://www.garbarino.com/producto/3a20ba7107"), 5 seconds)
    result match {
      case SuccessfulResponse(json) => println(json)
      case _ => println("Something went wrong")
    }
  }

}
