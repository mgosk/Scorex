package examples.curvepos

import akka.actor.{ActorRef, Props}
import examples.curvepos.transaction.{SimpleBlock, SimpleTransaction, SimpleWallet}
import io.circe.Json
import scorex.core.api.http.ApiRoute
import scorex.core.app.{Application, ApplicationVersion}
import scorex.core.network.message.MessageSpec
import scorex.core.settings.Settings
import scorex.core.transaction.box.proposition.PublicKey25519Proposition
import scorex.core.transaction.wallet.Wallet

/**
  * Simple application implementing simple transactions
  * (just transfers from one pubkey to another)
  * and Nxt-like(simplified) Consensus
  */
class SimpleApp extends Application {
  override implicit val settings: Settings = new Settings {
    override def settingsJSON: Map[String, Json] = Map()
  }

  override lazy val applicationName: String = "SimpleApp"

  override lazy val appVersion: ApplicationVersion = ApplicationVersion(0, 1, 0)

  override type P = PublicKey25519Proposition
  override type TX = SimpleTransaction
  override type PMOD = SimpleBlock

  override type NVHT = SimpleNodeViewHolder

  override protected val additionalMessageSpecs: Seq[MessageSpec[_]] = Seq()
  override val apiTypes = Seq()
  override val apiRoutes: Seq[ApiRoute] = Seq()
  override val wallet: Wallet[P, TX, _] = SimpleWallet()

  override lazy val nodeViewHolderRef: ActorRef = actorSystem.actorOf(Props(classOf[SimpleNodeViewHolder]))
}

object SimpleApp extends App {
  new SimpleApp().run()
}