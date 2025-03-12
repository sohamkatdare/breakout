package game;

import java.awt.*;
import java.util.ArrayList;

public class BlockManager {
    private ArrayList<Block> blocks;

    public class Block extends Polygon {
        private boolean visible;
        private Color color;
        int height;

        public Block(int width, int height, int posX, int posY, Color blockColor) {
            super(new Point[] {
                new Point(0, 0),
                new Point(width, 0),
                new Point(width, height),
                new Point(0, height)
            }, new Point(posX, posY), 0);
            
            this.visible = true;
            this.color = blockColor;
            this.height = height;
        }

        public boolean checkCollision(Ball ball) {
            if (!visible) {
                return false;
            }
            
            Point[] ballPoints = ball.getPoints();
            for (Point p : ballPoints) {
                if (this.contains(p)) {
                    return true;
                }
            }
            
            return false;
        }

        public void handleCollision() {
            this.visible = false;
        }

        public boolean isVisible() {
            return visible;
        }

        public void paint(Graphics brush) {
            if (!visible) {
                return;
            }
            
            Point[] points = super.getPoints();
            int[] xVals = new int[points.length];
            int[] yVals = new int[points.length];
            
            for (int i = 0; i < points.length; i++) {
                xVals[i] = (int)Math.round(points[i].getX());
                yVals[i] = (int)Math.round(points[i].getY());
            }
            
            Color prevColor = brush.getColor();
            brush.setColor(this.color);
            brush.fillPolygon(xVals, yVals, points.length);
            
            brush.setColor(Color.BLACK);
            brush.drawPolygon(xVals, yVals, points.length);
            
            brush.setColor(prevColor);
        }
    }

    public BlockManager() {
        blocks = new ArrayList<Block>();
    }

    public Block createBlock(int width, int height, int posX, int posY,
    		Color blockColor) {
        Block block = new Block(width, height, posX, posY, blockColor);
        blocks.add(block);
        return block;
    }
    
    public void createBlockGrid(int rows, int cols, int blockWidth, 
    		int blockHeight, int startX, int startY, int padding, 
    		Color[] colors) {
        for (int row = 0; row < rows; row++) {
            Color rowColor = colors[row % colors.length];
            
            for (int col = 0; col < cols; col++) {
                int x = startX + col * (blockWidth + padding);
                int y = startY + row * (blockHeight + padding);
                
                createBlock(blockWidth, blockHeight, x, y, rowColor);
            }
        }
    }
    
    public int checkCollisions(Ball ball) {
        int collisionOccurred = 0;
        
        for (Block block : blocks) {
            if (block.isVisible() && block.checkCollision(ball)) {
                block.handleCollision();
                
                if (ball.position.getY() + ball.getRadius() > 
                		block.position.getY() + block.height / 2 && 
                		ball.position.getY() - ball.getRadius() < 
                		block.position.getY() - block.height / 2) {
                	collisionOccurred = 1;
                } else if (ball.position.getY() + ball.getRadius() > 
                			block.position.getY() + block.height / 2) {
                	collisionOccurred = 2;
                } else {
                	collisionOccurred = 3;
                }
            }
        }
        
        return collisionOccurred;
    }

    public boolean allBlocksDestroyed() {
        for (Block block : blocks) {
            if (block.isVisible()) {
                return false;
            }
        }
        return true;
    }
    
    public void paint(Graphics brush) {
        for (Block block : blocks) {
            block.paint(brush);
        }
    }
    
    public int getRemainingBlockCount() {
        int count = 0;
        for (Block block : blocks) {
            if (block.isVisible()) {
                count++;
            }
        }
        return count;
    }
}
