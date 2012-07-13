/**
 * 
 */
package CompilerTest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;
import org.junit.rules.TestWatchman;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;

import cse431.lab5.Compiler;
import cse431.lab5.CopyOfCompilerToPrintAST;

/**
 * @author David Julia
 *
 */
public class compilerTest {
	
	final static String myTestFile = "test1.djav"; //Change myTestFile to the your test File's name 
	//Change myJavaExecutable to your System's java executable location
	//It should just work if you're using OSX (and probably any flavor of unix but I'm not sure)
	final static String myJavaExecutable = System.getProperty("java.home") +"/bin/java";
	static File myASTLogFile;
	////////////// Constants /////////////////////////////
	//////To be used for making composite tests///////////
	final static String MY_ADD_METHOD =  
			"\nint myAdd(int x, int y) {\n" +
			 "return x + y;\n}\n";
	
	//////////////////////////////////////////////////////
	@Rule
    public TemporaryFolder folder= new TemporaryFolder();
	@Rule
    public TestWatchman astTestNameLogger = new TestWatchman(){
		@Override
		public void starting( FrameworkMethod method){
			try {
				FileWriter fstream = new FileWriter(myASTLogFile, true);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(
						"**********************************************************************************\n"+
						method.getName()+"\n"+
				 		"--------------------------------------------------------------------------------\n\n"
						);
			 out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	 };
	
	@BeforeClass
	public static void writeToASTLogHeader(){
		myASTLogFile = new File("test/testASTLog.txt");
		try {
			FileWriter fstream = new FileWriter(myASTLogFile, false);
			BufferedWriter out = new BufferedWriter(fstream);
			String testSuiteTime = (new java.util.Date()).toString();
			out.write(
					"+++++++++++++++++++++++++++++AST'S FOR COMPILER TEST++++++++++++++++++++++++++++++\n" +
					"=========================="+testSuiteTime +"============================\n"+
			 		"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n");
			 out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	///////////////////////////////////////////////////
	
	
	@Test
	public void visitAssignNode() throws Exception {
	  String testContents= addBoilerPlate(
				 "int i, j, k, z;\n" +
				 "i = 10;\n"+
				 "j = 10;\n"+
				 "i = 1;\n"+
				 "k = i;\n"
				 );
		 
	     File createdFile= createTempTestFile(testContents);
	     assertCorrectAndLog(createdFile);
	  }
	
	@Test
	public void visitAdditionNode() throws Exception {
	  String testContents= addBoilerPlate(
				 "int i, j, k, z;\n" +
				 "i = 10;\n"+
				 "j = 10;\n"+
				 "k = i+j;\n"+
				 "z = k+z + 111 + i;"
				 );
		 
	     File createdFile= createTempTestFile(testContents);
	     assertCorrectAndLog(createdFile);
	  }
	
	 @Test
	 /**
	  * Tests the If statement node, but doesn't test the boolean operations
	  * @throws Exception
	  */
		public void visitIfStatementNode() throws Exception {
		  String myTestContent = addBoilerPlate(
		  		"if(false){\n" +
		  		"int k = 777;\n" +
		  		"}\n" +
		  		"else if(true){\n" +
		  		"}\n" +
		  		"else{\n" +
		  		"}\n"
		  		);
		  File testFile = createTempTestFile(myTestContent);
		  assertCorrectAndLog(testFile);
		  }
	 @Test
		public void visitBooleanLiteralNode() {
	    	String myTestContent = addBoilerPlate(
	    		"if(false){\n" +
		  			"if(true){\n" +
		  			"}\n" +
		  		"}\n" +
		  		"else if(false){\n" +
		  		"}\n" +
		  		"if(true){\n" +
		  		"}\n");
		  
		  File testFile = createTempTestFile(myTestContent);
		  assertCorrectAndLog(testFile);
		  }
     @Test
 	public void visitBooleanAndNode() throws Exception {
 	  String myTestContent = addBoilerPlate("\n" +
 	  		"if( true && false &&true){\n" +
				 "\n}"+
			"else if(false && false){\n" +
			"}\n"+
			"else if(true && true && true){"+
			"}\n"+
			"if(true && true){\n"+
			"}\n"
				 );
 	  
 	  File testFile = createTempTestFile(myTestContent);
 	  assertCorrectAndLog(testFile);
 	  }
   

    @Test
    public void visitBooleanNotNode() throws Exception {
	  String myTestContent = addBoilerPlate(
			 "if( true && !(false && !true) ){\n" +
				 "\n}"+
			"else if(!false && !false){\n" +
			"}\n"+
			"else if(!true && !true && !true){"+
			"}\n"+
			"if(!(true && true)){\n"+
			"}\n");
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  }

    @Test
	public void visitBooleanOrNode() throws Exception {
    	 String myTestContent = addBoilerPlate(
    			 "if( true || (false || true) ){\n" +
    				 "\n}"+
    			"else if(false || false){\n" +
    			"}\n"+
    			"else if(true || true || true){"+
    			"}\n"+
    			"if(false || true || false || true || true || false){\n"+
    			"}\n");
	  
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
}

    @Test
	public void visitDivisionNode() throws Exception {
    	String testContents= addBoilerPlate(
   			 "int i, j, k;\n" +
   			 "i = 10;\n"+
   			 "j = 10;\n"+
   			 "k = i/j;\n"
   			 );
   	 
        File testFile= createTempTestFile(testContents);
        assertCorrectAndLog(testFile);
	  }

    @Test
	public void visitEqualNode() throws Exception {
         // FIXME
    	 String myTestContent = addBoilerPlate(
    			 "if( 6 == 6 ){\n" +
    					 "int testIfGetHere;\n"+
    					 "testIfGetHere=99999;\n"+
        				 "\n}"+
    			 "if( 6 == 6 ){\n" +
    				 "\n}"+
    			"if(10 == 34){\n"+
    			"}\n" +
    			"int i, j;\n"+
    			"i = 6789;\n"+
    			"j= 12345;\n"+
    			"if(i==j){\n"+
    			"}\n"+
    			"if( true == false ){\n" +
				 "\n}"+
				 "if( 1 == true ){\n" +
				 "\n}"+
				 "if( 0 == false ){\n" +
				 "\n}"+
				 "if( true == true ){\n" +
				 "\n}"+
				 "if( (true == true) == false ){\n" +
				 "\n}"
    			 );
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  
	  }
    
    @Test
	public void visitGreaterThanNode() throws Exception {
	  String myTestContent = addBoilerPlate(
			  "int i,j;\n" +
			  "i= 999;\n" +
			  "j = 2;\n"+		  
			  "if( 4 > 7 ){\n" +
			 "\n}"+
			 "if( 1 > 9999 ){\n" +
			 "\n}"+
			 "if( i > j ){\n" +
			 "\n}"
			);
	  
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  }

    @Test
	public void visitGreaterThanOrEqualNode() throws Exception {
	  String myTestContent = addBoilerPlate( 
			  "int i,j;\n" +
			  "i= 999;\n" +
			  "j = 2;\n"+		  
			  "if( 4 >= 7 ){\n" +
			 "\n}"+
			 "if( 1 >= 1 ){\n" +
			 "\n}"+
			 "if( i >= j ){\n" +
			 "\n}"
			 );
	  
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  }

    @Test
	public void visitIntegerLiteralNode() {
	  String myTestContent = addBoilerPlate("int i,j,k,z;\n" +
			  "i= 999;\n" +
			  "j = 2;\n"+
			  "k = 0;\n"+
			  "z = 92834;\n"
			  );
	  
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  
	  }
    
    @Test
	public void visitLessThanNode() throws Exception {
	  String myTestContent = addBoilerPlate( 
			  "int i,j;\n" +
			  "i= 999;\n" +
			  "j = 2;\n"+		  
			  "if( 4 < 7 ){\n" +
			 "\n}"+
			 "if( 9999 < 12 ){\n" +
			 "\n}"+
			 "if( i < j ){\n" +
			 "\n}");
	  
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  
	  }

    @Test
	public void visitLessThanOrEqualNode() throws Exception {
    	String myTestContent = addBoilerPlate( 
  			  "int i,j;\n" +
  			  "i= 8;\n" +
  			  "j = 999;\n"+		  
  			  "if( 4 <= 7 ){\n" +
  			 "\n}"+
  			 "if( 1 <= 1 ){\n" +
  			 "\n}"+
  			 "if( i <= j ){\n" +
  			 "\n}"
  			 );
	  
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  
	  }

    @Test
	public void visitMultiplicationNode() throws Exception {
	  String myTestContent = addBoilerPlate(
			  	"int i, j, k, z;\n" +
				 "i = 10;\n"+
				 "j = 10* (1+2);\n"+
				 "k = i*j;\n"+
				 "z = k*z * 111 * i;");
	  
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  }

    @Test
	public void visitNotEqualNode() throws Exception {
         // FIXME
	  String myTestContent = addBoilerPlate(
			 "if(10 != 34){\n"+
  			"}\n" +
  			"int i, j;\n"+
  			"i = 7;\n"+
  			"j= 7;\n"+
  			"if(i!=j){\n"+
  			"}\n"+
  			"if( true != false ){\n" +
			 "\n}"+
			 "if( 1 != true ){\n" +
			 "\n}"+
			 "if( 0 != false ){\n"+
			 "}\n"
				 );
	  
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  }

    @Test
	public void visitReturnStatementNode() throws Exception {
	  String myTestContent = addBoilerPlate("",
			  MY_ADD_METHOD +
			  "int myTestIntReturn()\n" +
			  "{\n" +
			  " return 4;"+
			  "}\n"+
			  "int myTestBoolReturn()\n" +
			  "{\n" +
			  " return true;"+
			  "}\n"+
			  "void myTestVoidReturn()\n" +
			  "{\n" +
			  " return;"+
			  "}\n"+
			  "void myTestVoidMissingReturn()\n" +
			  "{\n" +
			  "}\n"
			  );
	  
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  
	  }

    @Test
	public void visitSubtractionNode() throws Exception {
	  String myTestContent = addBoilerPlate(
			  	 "int i, j, k, z;\n" +
				 "i = 10;\n"+
				 "j = 10;\n"+
				 "k = i - j;\n"+
				 "z = k - z - 111 - i;");
	  
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  }
    
    @Test
	public void visitUnaryMinusNode() throws Exception {
         // FIXME
	  String myTestContent = addBoilerPlate(
			  "int i, j, k, z;\n" +
				 "i = -10;\n"+
				 "j = -10;\n"+
				 "k = -(i+j);\n"+
				 "z = 4;\n"+
				 "z = -4;\n"
				 );
	  
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  
	  }

    @Test
	public void visitUnaryPlusNode() throws Exception {
	  String myTestContent = addBoilerPlate(
			 "int i, j, k, z;\n" +
			 "i = +999;\n"+
			 "j = +10;\n"+
			 "k = +(i+j);\n"+
			 "z = +4;\n"+
			 "z = +4;\n"
			 );
	  
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  
	  }
    
    @Test
	public void visitWhileStatementNode() throws Exception {
         // FIXME
	  String myTestContent = addBoilerPlate(
			  "int i, j;\n"+
			  "i=0;\n"+
			  "j = 5;\n"+
			  "while(i<j){\n"+
			  "i= i+1;\n"+
			  "\n}"
			);
	  
	  File testFile = createTempTestFile(myTestContent);
	  assertCorrectAndLog(testFile);
	  }
	
     @Test 
     public void testMethodAccess(){
    	 String testContents=addBoilerPlate(
    			 "int k;\n"+	
    			"k=myAdd(1, k);\n",
    			 
    			MY_ADD_METHOD
    			);
    	 File createdFile= createTempTestFile(testContents);
         assertCorrectAndLog(createdFile);
    		
     }
     
 	/**
 	 * Test method for {@link cse431.lab5.Compiler#run(java.lang.String[])}.
 	 */
 	@Test
 	public void testOverallCompilerOutput() {
 			File myTestFile = new File("test/"+compilerTest.myTestFile);
 			assertCorrectAndLog(myTestFile);	
 	}

     
	
	
 	/////////////////////////////////////////////////////////////////
 	//////////////////////   TEST HELPER METHODS    /////////////////
	/////////////////////////////////////////////////////////////////
	
    private String addBoilerPlate(String program)
 	{
 		return addBoilerPlate(program, "");
 	} 
    private String addBoilerPlate(String program, String otherMethods)
	{
		return
		"class Foo {\n\n"+
	    "void program() {\n"+
	    program +
	    "\n}\n" +
	    otherMethods+
	    "\n}\n\n";
	}
     
     private String getMyCompilerOutput(File testFile)
	{
		PrintStream stdout = System.out;
		//Redirect the output stream to my output stream for testing
		final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
		
		try{
			System.setOut(new PrintStream(myOut));
			//Run my compiler
			String testFilePath = testFile.getAbsolutePath();
			String[] args = {testFilePath};
			Compiler.main(args);
		}
		finally{
			System.setOut(stdout); //reset to standard output stream
		}
		return myOut.toString();
		
	}
	
	private String getReferenceOutput(File testFile) throws IOException, InterruptedException
	{
		ProcessBuilder pb = new ProcessBuilder(this.myJavaExecutable , "-jar", "decaf.jar", testFile.getAbsolutePath());
		pb.directory(new File("test"));
		Process p;
		p = pb.start();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(p.getInputStream())
		);
		p.waitFor();
		String referenceOutput ="";
		String tempOut;
		while (( tempOut= br.readLine()) != null) {
			referenceOutput+= tempOut+"\n";
		}
		return referenceOutput;
	}
	
	private void assertCorrectAndLog(File myTestFile) {
		try{
			appendAstToLog(myTestFile);
			assertEquals( "Your compiler's output is not the same as the reference compiler output.",
					getReferenceOutput(myTestFile), 
					getMyCompilerOutput(myTestFile)
					);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail("Ooops. There was probably an IO exception, or more likely the file specified doesn't exist. Test invalid");
		}
	
	}
	
	private void appendAstToLog(File myTestFile){
		 FileWriter fstream;
		try {
			fstream = new FileWriter(myASTLogFile, true);
		
		 BufferedWriter out = new BufferedWriter(fstream);
		 
		 PrintStream stdout = System.out;
			//Redirect the output stream to my output stream for testing
			final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
			
			try{
				System.setOut(new PrintStream(myOut));
				//Run my compiler
				String testFilePath = myTestFile.getAbsolutePath();
				String[] args = {testFilePath};
				CopyOfCompilerToPrintAST.main(args);
			}
			finally{
				System.setOut(stdout); //reset to standard output stream
			}
			String astOutput = myOut.toString();
		 
		  out.write(astOutput+"\n");
		 out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private File createTempTestFile(String contents) {
		File tempFile = new File("meta-temp");
		try{
		 tempFile =  folder.newFile("temp.djav");
		 FileWriter fstream = new FileWriter(tempFile);
		 BufferedWriter out = new BufferedWriter(fstream);
		 out.write(contents);
		 out.flush();
		 return tempFile;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return tempFile;
	}

}
