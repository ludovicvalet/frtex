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

package actors.impl.base;

import actors.ActorRefImpl;
import actors.Message;
import actors.impl.decent.DecentActorRef;

import actors.exceptions.ShouldNotHappenException;

public class BaseActorRef<T extends Message> extends ActorRefImpl<T>
{
    public DecentActorRef upgradeToDecentActorRef()
    // return a DecentActorRef with the same properties.
    // - copy the actor system
    // - the user has to give the accepted type.
    {
        DecentActorRef decent = new DecentActorRef();
        decent.setActorSystem(getActorSystem());
        return decent;
    }
}
