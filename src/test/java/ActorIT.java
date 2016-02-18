/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Riccardo Cardin
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
package actors;

import actors.utils.ActorSystemFactory;
import actors.utils.actors.TrivialActor;
import actors.utils.actors.counter.CounterActor;
import actors.utils.actors.ping.pong.PingPongActor;
import actors.utils.actors.StoreActor;
import actors.utils.messages.StoreMessage;
import actors.utils.messages.counter.Increment;
import actors.utils.messages.ping.pong.PingMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration test suite on actor features.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public class ActorIT {

    private ActorSystem system;

    /**
     * Initializes the {@code system} with a concrete implementation before each test.
     */
    @Before
    public void init() {
        System.out.println("ActorSystemFactory en action");
        this.system = ActorSystemFactory.buildActorSystem();
    }

    //@Test
    public void shouldBeAbleToSendAMessage() throws InterruptedException {
        System.out.println("LANCEMENT DE 'shouldBeAbleToSendAMessage'");
        ActorRef ar = system.actorOf(StoreActor.class);
        TestActorRef ref = new TestActorRef(system.actorOf(StoreActor.class));
        StoreActor actor = (StoreActor) ref.getUnderlyingActor(system);
        System.out.println("getUnderlyingActor ... fait");
        // Send a string to the actor
        ref.send(new StoreMessage("Hello World"), ref);
        System.out.println("ActorIT : message envoyé");
        // Wait that the message is processed
        Thread.sleep(2000);
        System.out.println("ActorIT : sleep fini");
        // Verify that the message is been processed
        Assert.assertEquals("The message has to be received by the actor", "Hello World", actor.getData());
        System.out.println("FIN DE 'shouldBeAbleToSendAMessage'");
    }

    @Test
    public void shouldBeAbleToRespondToAMessage() throws InterruptedException {
        System.out.println("LANCEMENT DE 'shouldBeAbleToRespondToAMessage'");
        TestActorRef pingRef = new TestActorRef(system.actorOf(PingPongActor.class));
        TestActorRef pongRef = new TestActorRef(system.actorOf(PingPongActor.class));

        pongRef.send(new PingMessage(), pingRef);

        System.out.println("ActorIR::avant sleep");
        Thread.sleep(2000);
        System.out.println("ActorIR::après sleep");

        PingPongActor pingActor = (PingPongActor) pingRef.getUnderlyingActor(system);
        PingPongActor pongActor = (PingPongActor) pongRef.getUnderlyingActor(system);

        //Assert.assertEquals("A ping actor has received a ping message", "Ping",
          //      pingActor.getLastMessage().getMessage());
        //Assert.assertEquals("A pong actor has received back a pong message", "Pong",
           //     pongActor.getLastMessage().getMessage());
        System.out.println("FIN DE 'shouldBeAbleToRespondToAMessage'");
    }

    //@Test
    public void shouldNotLooseAnyMessage() throws InterruptedException {
        System.out.println("LANCEMENT DE 'shouldNotLooseAnyMessage'");
        TestActorRef counter = new TestActorRef(system.actorOf(CounterActor.class));
        for (int i = 0; i < 200; i++) {
            TestActorRef adder = new TestActorRef(system.actorOf(TrivialActor.class));
            adder.send(new Increment(), counter);
        }

        Thread.sleep(2000);

        Assert.assertEquals("A counter that was incremented 1000 times should be equal to 1000",
                200, ((CounterActor) counter.getUnderlyingActor(system)).getCounter());
    }
}
