package spinoco.fs2.cassandra.util

object KotlinSyntax {
    implicit class KotlinSyntax[R](val self: R) extends AnyVal {
      def also(f: R => Any): R = {
        f(self)
        self
      }

      def let[O](f: R => O): O = {
        f(self)
      }
    }
}
