object EMail {
  def apply(user: String, domain: String) = user + "@" + domain

  def unapply(str: String) = {
    val parts = str split "@"
    if (parts.length == 2) Some(parts(0), parts(1)) else None
  }
}

object ExpandedEMail {
  def unapplySeq(email: String): Option[(String, Seq[String])] = {
    val parts = email split "@"
    if (parts.length == 2)
      Some(parts(0), parts(1).split("\\.").reverse)
    else
      None
  }
}

object Twice {
  def apply(s: String) = s + s

  def unapply(s: String) = {
    val length = s.length / 2
    val half = s.substring(0, length)
    if (half == s.substring(length)) Some(half) else None
  }
}

object UpperCase {
  def unapply(s: String) = s.toUpperCase == s
}

object Domain {
  def apply(parts: String*) = parts.reverse.mkString(".")

  def unapplySeq(whole: String): Option[Seq[String]] = Some(whole.split("\\.").reverse)
}

object Main {
  def userTwiceUpper(s: String) = s match {
    case EMail(Twice(x @ UpperCase()), Domain(domain @ _*)) =>
      "match: " + x + " in domain" + domain
    case _ => "no match"
  }

  def isTomInDotCom(s: String) = s match {
    case EMail("tom", Domain(domain @ _*)) => true
    case _ => false
  }
}
