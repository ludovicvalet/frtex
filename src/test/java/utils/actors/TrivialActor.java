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


package actors.utils.actors;

import actors.AbsActor;
import actors.utils.messages.TrivialMessage;

/**
 * A trivial actor that do nothing.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public class TrivialActor extends AbsActor<TrivialMessage> {
    @Override
    public void receive(TrivialMessage message) {
        // Do nothing.
    }
}