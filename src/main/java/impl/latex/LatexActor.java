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

package actors.impl.latex;

import java.util.HashMap;
import actors.exceptions.ShouldNotHappenException;
import actors.impl.minimum.MinAbsActor;

// a LatexActor is 'working' until it succeed to send the answer to who asked that.
class LatexActor extends MinAbsActor<LatexMessage>
{
    private String filename;
    private HashMap<String,String> inputed_filenames;
    private Boolean working;

    // For each inputed file, the map 'inputed_filenames' retains its content.
    LatexActor() 
    {
        super();
        inputed_filenames=new HashMap<String,String>();
        working=true;
    }
    private void processMessage(LatexMessage message)
    {
        String tag=message.getTag();
        synchronized(working)
        {
            if (tag.equals("aks") && working)
            {
                throw new ShouldNotHappenException("One is asking me to deal with a new file while I'm not done with my previous work.");
            }
            if (tag.equals("ask")) { working=true; }
        }
        if (tag.equals("answer"))
        {
            //AnswerProcessing answer_process = new AnswerProcessing(message,getActorRef(),m.getSender());
            //Thread t = new Thread(answer_process);
            //t.start();
        }
        if (tag.equals("ask"))
        {
            FileProcessing file_processing = new FileProcessing( this,filename );
            Thread t = new Thread(file_processing);
            t.start();
        }
    }
}