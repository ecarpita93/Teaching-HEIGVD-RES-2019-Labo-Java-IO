package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor visitor) {

    TreeSet<File> folderToVisit = new TreeSet();

    // if File exists
    if (rootDirectory.exists()) {


      // if File is a directory
      if (rootDirectory.isDirectory()) {
        visitor.visit(rootDirectory);


        // we add it to the treeset (sorted)
        for (File file : rootDirectory.listFiles()) {
          folderToVisit.add(file);
        }
        // (we explore every directory before proceeding)
        for (File file : folderToVisit) {
          explore(file, visitor);
        }

      } else {
        // if it's a File we apply the visit
        visitor.visit(rootDirectory);
      }

    // if noFile
    } else {
      visitor.visit(rootDirectory);
    }

  }

}
