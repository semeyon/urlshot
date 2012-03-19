package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class ModelSpec extends Specification {
    
    import models._

    val inMD = inMemoryDatabase()
    
    "Url model" should {
        
        "create url" in {
            running(FakeApplication(additionalConfiguration = inMD)) {
                
                Url.create(Url(url="http://ixbt.com"))
                
                val urls = Url.findAll()
                urls must have size(1)

                val u = urls(0)
                u.url must equalTo("http://ixbt.com")
                u.id.get.toInt  must equalTo(1)
            }
            
        }

        "get Url by string url and id" in {
            running(FakeApplication(additionalConfiguration = inMD)) {
            
                Url.create(Url(url="http://ixbt.com"))
                Url.create(Url(url="http://nic.ru"))

                val iurl = Url.getById(1)
                iurl.get.url must equalTo("http://ixbt.com")

                val nurl = Url.getByUrl("http://nic.ru")
                nurl.get.url must equalTo("http://nic.ru")
            
            }
        }

        "increase counter" in {
            running(FakeApplication(additionalConfiguration = inMD)) {
            
                Url.create(Url(url="http://ixbt.com"))

                Url.incCounter(1)
                Url.incCounter(1)
                Url.incCounter(1)

                val url = Url.getById(1)
                url.get.counter must equalTo(3)
            
            }
        }

        "get or create Url" in {
            running(FakeApplication(additionalConfiguration = inMD)) {
                
                val host ="localhost"
                val url = Url(url="http://ixbt.com")

                val r0Url = Url.getOrCreate(url, host)
                r0Url must equalTo("http://localhost/b")

                val r1Url = Url.getOrCreate(url, host)
                r1Url must equalTo("http://localhost/b")
            
            }
        }

        

    }
}
