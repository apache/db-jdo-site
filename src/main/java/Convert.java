import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import club.caliope.udc.DocumentConverter;
import club.caliope.udc.InputFormat;
import club.caliope.udc.OutputFormat;


/**
 * This class converts the old generated HTML files to ASCIIDOC file.
 * The paths are hardcoded and assume src and target to have the same base folder.
 * The html menu can be configured in template/document.html.erb
 * 
 * Requirements:
 * * Pandoc: https://pandoc.org/
 * 
 * To create HTML from the generated ASCIIDOC:
 * * mvn clean compile
 * * Copy "target/site"to "docs".
 * 
 * Link to view html locally in browser:
 *  file:///D:/projects/workspace-zoo/jdo.asciidoc/target/site/
 * 
 * @author Tilmann Zäschke
 */
public class Convert {

	private static final String NL = "\n"; 
	// Base folder for src and output files
	private static final Path basePath = Paths.get("C:\\work\\eclipse-workspace\\jdo.site");
	// Input folder
	private static final String input = "docs";
	// Output folder
	private static final String output = "adoc-out";

	public static void main(String[] args) throws IOException {
		Path srcPath = basePath.resolve(input);

		String executionPath = System.getProperty("user.dir");
		System.out.println(executionPath);

		Files.find(srcPath, 10, (p, a) -> { return true; }).forEach(p -> convert(p.toFile()));
	} 

	private static void convert(File src) {
		if (src.toString().contains("docs\\api")) {
			// skip 'api' folders
			return;
		}
		if (!src.toString().endsWith(".html")) {
			// skip non-html: css, ...
			return;
		}

		try {
			String srcName = src.getName();
			srcName = srcName.substring(0, srcName.lastIndexOf(".") + 1);
			String dstName = srcName + "adoc";

			Path srcPath = src.toPath();
			Path relPath = basePath.relativize(srcPath);

			int count = relPath.getNameCount();
			Path fullOutPath;
			if (count > 2) {
				//System.out.println("re2: " + relPath.subpath(1, count-1).toString());
				fullOutPath = Paths.get(basePath.toString(), output, relPath.subpath(1, count-1).toString(), dstName.toString());
			} else {
				fullOutPath = Paths.get(basePath.toString(), output, dstName.toString());
			}

			if (!fullOutPath.getParent().toFile().exists()) {
				Files.createDirectories(fullOutPath.getParent());
			}

			Files.deleteIfExists(fullOutPath);
			File outFile = Files.createFile(fullOutPath).toFile();
			new DocumentConverter()
			.fromFile(src, InputFormat.HTML)
			.toFile(outFile, OutputFormat.ASCIIDOC)
			//	     .addOption("-s")                     //optional
			//	     .workingDirectory(new File("/tmp"))  //optional
			.convert();
			postConvert(outFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static void postConvert(File dst) {
		BufferedReader br;
		BufferedWriter bw;
		String s;

		File tempDst = Paths.get(dst.toString() + ".tmp").toFile();
		dst.renameTo(tempDst);
		try {
			bw = new BufferedWriter(new FileWriter(dst, true));
			try {
				br = new BufferedReader(new FileReader(tempDst));

				// Write new header
				bw.write(header());

				// Skip old header
				while((s = br.readLine()) != null) {
					if (s.startsWith("[[contentBox]]")) {
						break;
					}
				}

				while((s = br.readLine()) != null) {
					//                	System.out.println("Checking: " + s);

					//Lines 1 and 2:
					// TODO ? Replace Images:
					//link:./[image:images/JDOx150.gif[Apache JDO]]
					//link:./[image:images/jdo_text.gif[Apache JDO]]
					//image:images/JDOx150.gif[Apache JDO]
					//image:images/jdo_text.gif[Apache JDO]

					// Fix external links, except for actual javadoc html pages 
//					if (!s.contains("-javadoc/index.html")) {
//						s = s.replace(".html", ".adoc");
//					}
					// Fix internal crossrefs
					s = s.replace("link:#", "xref:");

					// Fix GIF not supported:
					s = s.replace(".gif", ".png");

					// Fix empty lines
					// see https://github.com/asciidoctor/asciidoctor/wiki/How-to-insert-sequential-blank-lines
					if (s.equals(" +")) {
						s = "{empty} +\n";
						bw.write(s + NL);
						continue;
					}

					// Fix crossref targets/anchors
					// INPUT:
					//[#PersistenceCapable]##
					//==== @PersistenceCapable[#aPersistenceCapable]####
					// OUPUT:
					//anchor:anchor-2[]
					while (s.contains("[#")) {
						int pos1 = s.indexOf("[#");
						int pos2 = s.indexOf("]", pos1+1);
						String post = "[" + s.substring(pos2);
						if (pos1 > 0) {
							s = s.substring(0, pos1) + "anchor:" + s.substring(pos1+2, pos2) + post;
						} else {
							s = "anchor:" + s.substring(2, pos2) + post;
						}
					}

					// some more hacks:
					s = s.endsWith("]##") ? s.substring(0, s.lastIndexOf(']') + 1) : s;
					s = s.endsWith("]####") ? s.substring(0, s.lastIndexOf(']') + 1) : s;
					// Fixes glossary.html
					int pos = s.indexOf("]####");
					if (pos >=0) {
						s = s.substring(0, pos + 1) + s.substring(pos + 5);
					}

					bw.write(s + NL);
				}
				br.close();
			} catch(FileNotFoundException e) {
				System.out.println("File was not found!");
			} catch(IOException e) {
				System.out.println("No file found!");
			}
			bw.close();
		} catch(FileNotFoundException e) {
			System.out.println("Error1!");
		} catch(IOException e) {
			System.out.println("Error2!");
		}
		tempDst.delete();
	}

	private static String header() {
		StringBuilder s = new StringBuilder();
		s.append("[[index]]").append(NL);
		
//		s.append("--").append(NL);
		s.append("image:images/JDOx150.png[float=\"left\"]").append(NL);
		s.append("image:images/jdo_text.png[float=\"left\"]").append(NL);
//		s.append("--").append(NL);
	
		// Hide title
		//s.append("= XYZ").append(NL);
		//s.append(":notitle:").append(NL);
		//s.append("== JDO TEST").append(NL);
		//s.append("link:./[image:images/JDOx150.gif[Apache JDO]]").append(NL);
		//s.append("link:./[image:images/jdo_text.gif[Apache JDO]]").append(NL);
		s.append(NL);
		s.append("'''''").append(NL);
		s.append(NL);
		s.append(":_basedir: ").append(NL);
		s.append(":_imagesdir: images/").append(NL);
		s.append(":notoc:").append(NL);
		s.append(":titlepage:").append(NL);
		s.append(":grid: cols").append(NL);
		s.append(NL);
		return s.toString();
	}
}
