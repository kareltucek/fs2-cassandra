package spinoco.fs2.cassandra.util

import com.datastax.oss.driver.api.core.cql.{AsyncResultSet, ResultSet, Row}

object DrainSyntax {
  implicit class ResultSetDrainSyntax(val self:ResultSet) extends AnyVal {
    def drain:Vector[Row] = {
      val count = self.getAvailableWithoutFetching
      spinoco.fs2.cassandra.util.iterateN(self.iterator(), count)
    }
  }

  implicit class AsyncResultSetDrainSyntax(val self: AsyncResultSet) extends AnyVal {
    def drain:Vector[Row] = {
      val count = self.remaining
      spinoco.fs2.cassandra.util.iterateN(self.currentPage.iterator, count)
    }
  }
}
