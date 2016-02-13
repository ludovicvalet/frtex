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

import java.util.Map;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Collection;
import java.util.Set;

import actors.exceptions.AlreadyListedActor;
import actors.exceptions.NoSuchActorException;
import actors.ActorRef;
import actors.Actor;
import actors.impl.base.BaseActorRef;
import actors.impl.base.BaseAbsActor;

// The 'ActorMap' is a wrapper for the two needed maps : 
// - one maps actor references to the actual actor
// - one maps actor references to the activity status.

public class ActorMap
{
    private Map<BaseActorRef,BaseAbsActor> actors_map;
    private Map<BaseActorRef,Boolean> active_map;

    public Collection<BaseAbsActor> actors_list() { return actors_map.values(); }
    public Set<BaseActorRef> actors_ref_list() { return actors_map.keySet(); }
    public BaseAbsActor getActor(BaseActorRef ref) 
    {
        if (!isActive(ref)) {throw new NoSuchActorException();}
        return actors_map.get(ref); 
    }

    public ActorMap()
    {
        actors_map = new HashMap<BaseActorRef,BaseAbsActor>();
        active_map = new HashMap<BaseActorRef,Boolean>();
    }
    public void put(BaseActorRef reference,BaseAbsActor actor)
    {
        for (BaseActorRef ref : actors_ref_list())
        {
            if (ref==reference) 
            {
                throw new AlreadyListedActor("This actor reference is already in the list.");
            }
        }
        actors_map.put(reference,actor);
        active_map.put(reference,true);
    }
    public void setActive(BaseActorRef ref,Boolean b){active_map.put(ref,b);}
    public Boolean isActive(BaseActorRef reference) {return active_map.get(reference);}
}