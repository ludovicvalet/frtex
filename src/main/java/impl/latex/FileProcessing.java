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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import java.util.Map;
import java.util.HashMap;

class FileProcessing implements Runnable
/*
   This class is intended to be launch in a separate thread. It fills the blocks of the 'decomposed' tex file of the calling actor.
   In the same time, the calling actor receive messages that fill the 'filename_to_content' map.
//*/
{
    private String filename;
    private LatexActor calling_actor;
    private DecomposedTexFile decomposed_file;
    private Boolean parsing;

    FileProcessing(String filename, DecomposedTexFile decomposed, LatexActor calling_actor)
    {
        this.filename=filename;
        this.decomposed_file=decomposed;
        this.calling_actor=calling_actor;
    }
    public Boolean isFinished()
    {
        if (parsing) {return false;}
        return !decomposed_file.stillWaiting();
    }
    public void makeSubstitution(String filename, String content)
    {
        decomposed_file.makeSubstitution(filename,content);
    }
    private String input_filename_to_filename(String input_filename)
    {
        if (input_filename.indexOf(".")>=0) { return input_filename; }
        return input_filename+".tex";
    }
    public void run() 
    {
        String line;
        try (
            InputStream fis = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            )
        {
            parsing=true;
            while ((line = br.readLine()) != null) 
            {
                int input_index = line.indexOf("\\input{");
                if (input_index>=0)
                {
                    int end_index=line.indexOf("}",input_index);
                    String input_filename=line.substring(input_index+7,end_index);
                    decomposed_file.newBlock(input_filename);
                    decomposed_file.addLine(line);
                    calling_actor.sendRequest(input_filename_to_filename(input_filename));
                }
                else 
                {
                    decomposed_file.addLine(line);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found : "+filename);
            decomposed_file.addLine("\\huge FILE NOT FOUND : "+filename);
        }
        catch (IOException e)
        {
            System.out.println("IO Error on file "+filename);
            decomposed_file.addLine("\\huge IO ERROR ON FILE : "+filename);
        }
        parsing=false;
    }
}
