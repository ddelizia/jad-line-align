package JADLineAlign;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
	    Collection<File> files = FileUtils.listFiles(new File(args[0]), new String[]{"java"}, true);

	    for (File file: files){
		    System.out.println("Fixing file "+ file.getAbsolutePath());
		    organizeFile(file);
	    }
    }

	private static void organizeFile(File file) {
		String regex = "/\\* *\\d+ *\\*/.*";
		try {
			ArrayList fileInList = new ArrayList(FileUtils.readLines(file));
			for (int i=0; i<fileInList.size(); i++){
				String line = (String)fileInList.get(i);

				Integer realLineNumber = -1;
			   	if (line.matches(regex)){
				    Pattern p = Pattern.compile("/\\* *\\d+ *\\*/");
				    Matcher m = p.matcher(line);
				    while (m.find()) {
					    Pattern pInside = Pattern.compile("\\d+");
					    Matcher mInside = pInside.matcher(m.group());
					    while (mInside.find()){
						    realLineNumber = Integer.valueOf(mInside.group());
					    }
				    }
			    }

				if (realLineNumber!=-1){
					int currentIndex = i;
					for (currentIndex=i ; currentIndex<realLineNumber-1; currentIndex++){
						fileInList.add(currentIndex,new String());
					}
				}
			}
			FileUtils.writeLines(file, fileInList);

		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
}
