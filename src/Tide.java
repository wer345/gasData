import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
// Sample code from :http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Draw2Dshape.htm
public class Tide extends JFrame {
  //Shape shapes[] = new Shape[1];
  
	List <Shape> shapes = new ArrayList <Shape> ();
	List <Line> lines = new ArrayList <Line>();
	
	int windowHeight=400;
	int windowWidth=600;
	
	class Point {
		double x;
		double y;
		
		Point(double _x, double _y) {
			x=_x;
			y=_y;
		}
	}
	
	class Range {
		double x1,x2,y1,y2;
		Range(double _x1, double _y1,double _x2, double _y2) {
			x1=_x1;
			y1=_y1;
			x2=_x2;
			y2=_y2;
		}
	}
	
	class Line {
	    List <Point> points = new ArrayList <Point>();
	    Color color = Color.black;
		List <Shape> lineShapes = new ArrayList <Shape> ();
		
	    Line(Color _color) {
	    	color=_color;
	    }
	    
	    void addPoint(Point point) {
	    	points.add(point);
	    }
	    
		void draw(Range range) {
			int size=points.size();
			double rx=windowWidth/(range.x2-range.x1);
			double ry=windowHeight/(range.y2-range.y1);
			if(size>=2) {
				for(int i=0;i<size-1;i++) {
					Point p1=points.get(i);
					Point p2=points.get(i+1);
					int p1x=(int)(rx*(p1.x-range.x1));
					int p2x=(int)(rx*(p2.x-range.x1));
					int p1y=windowHeight - (int)(ry*(p1.y-range.y1));
					int p2y=windowHeight - (int)(ry*(p2.y-range.y1));
					Shape shape=new Line2D.Double(p1x, p1y,p2x, p2y);
					lineShapes.add(shape);
				}
			}
		}

	}
	
	void drawLine(List <Point> points, Range range) {
		int size=points.size();
		double rx=windowWidth/(range.x2-range.x1);
		double ry=windowHeight/(range.y2-range.y1);
		if(size>=2) {
			for(int i=0;i<size-1;i++) {
				Point p1=points.get(i);
				Point p2=points.get(i+1);
				int p1x=(int)(rx*(p1.x-range.x1));
				int p2x=(int)(rx*(p2.x-range.x1));
				int p1y=windowHeight - (int)(ry*(p1.y-range.y1));
				int p2y=windowHeight - (int)(ry*(p2.y-range.y1));
				Shape shape=new Line2D.Double(p1x, p1y,p2x, p2y);
				shapes.add(shape);
			}
		}
	}
	
	
  public static void main(String args[]) {
    Tide app = new Tide();
  }


  void chart1()
  {
    List <Point> line1 = new ArrayList <Point>();
    line1.add(new Point(1,1));
    line1.add(new Point(2,2));
    line1.add(new Point(3,2));
    drawLine(line1, new Range(0.9,0.9,3,2.02));
  }
  
  void chart2()
  {
    List <Point> line1 = new ArrayList <Point>();
    List <Point> line2 = new ArrayList <Point>();
    for(double x=0;x<=2*Math.PI;x+=Math.PI/200) {
        line1.add(new Point(x,Math.sin(x)));
        line2.add(new Point(x,Math.cos(x)));
    }
    
    Range range=new Range(-0.1,-1.1,  2*Math.PI,1.1);
    drawLine(line1, range);
    drawLine(line2, range);
  }
  
  void heights()
  {
	    //List <Point> listH = new ArrayList <Point>();
	    //List <Point> listF = new ArrayList <Point>();
	    //List <Point> listV = new ArrayList <Point>();
	    
	    Line listH = new Line(Color.blue);
	    Line listF = new Line(Color.green);
	    Line listV = new Line(Color.red);
	    Line listW = new Line(Color.cyan);
	    
	    lines.add(listH);
	    lines.add(listF);
	    lines.add(listV);
	    lines.add(listW);

	    double x=0.6;
	    double A=100;
	    int stepT=60;
	    int daySeconds=86400;
	    double g=9.8;
	    double stepTA=2*Math.PI/daySeconds;
	    double f=1.5; //pool height
	    double S=2.0; // hole section 
	    double rou=1000;
	    double sumW=0;
	    for (double t=0;t<=daySeconds;t+=stepT) {
	    	double ta = stepTA*t;
	    	double h=1.5+Math.sin(ta)+ 0.001*Math.sin(0.6+2*ta);
	    	boolean forward=true;
	    	double diffH=0;
	    	if(h>=f) {
	    		forward=true;
	    		diffH=h-f;
	    	}
	    	else {
	    		forward=false;
	    		diffH=f-h;
	    	}
	    	double v=Math.sqrt(2*(1-x)*g*diffH);
	    	if(!forward)
	    		v=-v;
	    	f+=v*S/A;
	    	if(forward) {
	    		if(f>h)
	    			f=h;
	    	}
	    	else {
	    		if(h>f)
	    			f=h;
	    	}
	    	double w=g*rou*(h-f)*S*v;
	    	sumW+=w;
	    	
	    	listH.addPoint(new Point(t,h));
	    	listF.addPoint(new Point(t,f));
	    	listV.addPoint(new Point(t,v));
	    	listW.addPoint(new Point(t,w));
	    	
	    }
	    
    	System.out.printf("Total energy output is %8.0f J\n", sumW);

	    Range range=new Range(-1000,-0.1, daySeconds ,3.1);
	    listH.draw(range);
	    listF.draw(range);
	    
	    Range rangeV=new Range(-1000,-4.5, daySeconds ,4.5);
	    listV.draw(rangeV);
	    
	    Range rangeW=new Range(-1000,-0.1, daySeconds ,2000);
	    listW.draw(rangeW);
	    
  }
  
  public Tide() {
    add("Center", new MyCanvas());
    
    //shapes.add(new Line2D.Double(0.0, 0.0,windowWidth,windowHeight));
    //shapes.add(new Line2D.Double(0.0, 0.0,windowWidth,0.5*windowHeight));
    
    //chart2();
    heights();
    
    setSize(windowWidth+25, windowHeight+50);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  class MyCanvas extends Canvas {
    public void paint(Graphics graphics) {
      Graphics2D g = (Graphics2D) graphics;
      for(Shape shape:shapes) {
    	  g.draw(shape);
      }
      for(Line line:lines) {
    	  g.setColor(line.color);
	      for(Shape shape:line.lineShapes) {
	    	  g.draw(shape);
	      }
      }

    }
  }
}