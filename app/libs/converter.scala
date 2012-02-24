package converter

object Converter {
    
    private val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private val base     = alphabet.length

    def encode(c : Int): String = c match {
        case 0 => alphabet(0) toString
        case i if i > 0 => {
            val v = 0 to c/base map(c/scala.math.pow(base, _)%base toInt) reverse 
            val s = v map(alphabet(_))
            s mkString
        } 
    }

    def decode(s: String): Int = 
        s map(alphabet.indexOf(_)) reduce((x,y)=>x*(x*base)+y)
        
}
