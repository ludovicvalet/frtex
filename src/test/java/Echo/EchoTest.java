/*
Copyright 2016 Laurent Claessens
contact : moky.math@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
//*/

// This is testing the Echo implementation of my actor system.

import actors.ActorRef;

import java.lang.InterruptedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import actors.impl.Echo.EchoActor;
import actors.impl.Echo.EchoActorRef;
import actors.impl.Echo.EchoActorSystem;
import actors.impl.Echo.EchoText;
import actors.impl.Echo.EchoTextOne;
import actors.impl.Echo.EchoTextTwo;
import actors.exceptions.UnsupportedMessageException;

public class EchoTest  
{
    private EchoActorSystem system; 
    private EchoActorRef echo_actor;
    private EchoActorRef echo_one_actor;
    private EchoActorRef echo_two_actor;

    @Before
    public void initialize()
    {
        system = new EchoActorSystem();

        echo_actor = system.actorOf();
        echo_one_actor = system.actorOf();
        echo_two_actor = system.actorOf();

        echo_one_actor.setAcceptedType(EchoTextOne.class);
        echo_two_actor.setAcceptedType(EchoTextTwo.class);
    }

    @Test
    public void Numbering()
    {
        EchoActorSystem system = new EchoActorSystem();
        EchoActorRef a1 = system.actorOf();
        EchoActorRef a2 = system.actorOf();

        // thanks to http://www.btaz.com/java/junit-4-error-reference-to-assertequals-is-ambiguous/
        Assert.assertEquals((int) a1.getSerieNumber(),0);
        Assert.assertEquals((int) a2.getSerieNumber(),1);

        Assert.assertEquals(a2.getAcceptedType(),EchoText.class);
    }
    @Test
    public void setAcceptedTypeVerification() throws InterruptedException
    {
        System.out.println("LANCEMENT de acceptedTypeVerification");

        echo_one_actor.setAcceptedType(EchoTextOne.class);
        echo_two_actor.setAcceptedType(EchoTextTwo.class);

        Assert.assertEquals(echo_actor.getAcceptedType(),EchoText.class);
        Assert.assertEquals(echo_one_actor.getAcceptedType(),EchoTextOne.class);
        Assert.assertEquals(echo_two_actor.getAcceptedType(),EchoTextTwo.class);
    }

    @Test
    public void acceptedTypeVerification() throws InterruptedException
    {
        EchoText mE = new EchoText(echo_actor,echo_actor,20);
        echo_actor.send(mE,echo_actor);
        Thread.sleep(1000);
        System.out.println("echo_actor "+echo_actor.getLastMessage().getData());
        Assert.assertEquals((int) echo_actor.getLastMessage().getData(),1);

        EchoText mA = new EchoText(echo_one_actor,echo_two_actor,20);
        echo_one_actor.send(mA,echo_two_actor);
        Thread.sleep(1000);
        System.out.println("echo_actor "+echo_one_actor.getLastMessage().getData());
        Assert.assertEquals((int) echo_actor.getLastMessage().getData(),1);


        EchoTextOne mO = new EchoTextOne(echo_actor,echo_actor,23);
        echo_actor.send(mO,echo_actor);
        Thread.sleep(1000);
        System.out.println("echo_actor"+echo_actor.getLastMessage().getData());
        Assert.assertEquals((int)echo_actor.getLastMessage().getData(),1);

        EchoTextOne mB = new EchoTextOne(echo_actor,echo_one_actor,23);
        echo_actor.send(mB,echo_one_actor);
        Thread.sleep(1000);
        Assert.assertEquals((int)echo_actor.getLastMessage().getData(),1);
    }
    @Test (expected = UnsupportedMessageException.class)  
    public void nonAcceptedTypeVerification() throws InterruptedException
    {
        System.out.println("LANCEMENT de nonAcceptedTypeVerification");

        EchoTextOne mO = new EchoTextOne(echo_actor,echo_two_actor,23);
        echo_actor.send(mO,echo_two_actor);
        Thread.sleep(1000);
        Assert.assertEquals((int)echo_actor.getLastMessage().getData(),1);
    }
}
