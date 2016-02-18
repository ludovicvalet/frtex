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

package actors;

import actors.ActorRef;
import actors.SendingThread;

public class ActorRefImpl<T extends Message> implements ActorRef<T>
{
    private ActorSystemImpl  actor_system;

    public void setActorSystem(ActorSystemImpl as)  {actor_system=as;}
    public ActorSystemImpl getActorSystem()  {return actor_system;}

    public AbsActor getActor() { return getActorSystem().getActor(this);  }

    @Override
    public void send(Message message, ActorRef to) 
    { 
        System.out.println("ActorRefImpl::send");
        getActorSystem().send(message,to);
    }
    @Override
    public int compareTo(ActorRef other) { return getActorSystem().compareRefs(this,other); }
    public void stop()
    {
        getActorSystem().stop(this);
    }
}
