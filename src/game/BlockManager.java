package game;

import java.awt.*;
import java.util.ArrayList;

/**
 * Manages the blocks in the Breakout game.
 * Handles block creation, collision detection, and rendering.
 */
public class BlockManager {
    private ArrayList<Block> blocks;

    /**
     * Represents a block in the game.
     * Each block has a position, size, color, and visibility state.
     */
    public class Block extends Polygon {
        private boolean visible;
        private Color color;
        int height;

        /**
         * Creates a new block with the specified dimensions and position.
         *
         * @param width The width of the block
         * @param height The height of the block
         * @param posX The x-coordinate of the block's position
         * @param posY The y-coordinate of the block's position
         * @param blockColor The color of the block
         */
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

        /**
         * Checks if the ball collides with this block.
         *
         * @param ball The ball to check collision with
         * @return true if the ball collides with this block, false otherwise
         */
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

        /**
         * Handles the collision by making the block invisible.
         */
        public void handleCollision() {
            this.visible = false;
        }

        /**
         * Getter method for block visibility.
         *
         * @return true if the block is visible, false otherwise
         */
        public boolean isVisible() {
            return visible;
        }

        /**
         * Renders the block on the screen.
         *
         * @param brush The graphics context to paint on
         */
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

    /**
     * Default Constructor - empty list of blocks.
     */
    public BlockManager() {
        blocks = new ArrayList<Block>();
    }

    /**
     * Creates a new block and adds it to the manager.
     *
     * @param width The width of the block
     * @param height The height of the block
     * @param posX The x-coordinate of the block's position
     * @param posY The y-coordinate of the block's position
     * @param blockColor The color of the block
     * @return The created block
     */
    public Block createBlock(int width, int height, int posX, int posY,
    		Color blockColor) {
        Block block = new Block(width, height, posX, posY, blockColor);
        blocks.add(block);
        return block;
    }
    
    /**
     * Creates a grid of blocks with the specified dimensions and colors.
     *
     * @param rows The number of rows in the grid
     * @param cols The number of columns in the grid
     * @param blockWidth The width of each block
     * @param blockHeight The height of each block
     * @param startX The starting x-coordinate of the grid
     * @param startY The starting y-coordinate of the grid
     * @param padding The padding between blocks
     * @param colors The colors to use for the rows
     */
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
    
    /**
     * Checks if the ball collides with any blocks and handles the collisions.
     *
     * @param ball The ball to check collisions with
     * @return 0 if no collision, 1 for side collision, 2 for bottom collision, 3 for top collision
     */
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

    /**
     * Checks if all blocks have been destroyed.
     *
     * @return true if all blocks are invisible, false otherwise
     */
    public boolean allBlocksDestroyed() {
        for (Block block : blocks) {
            if (block.isVisible()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Renders all visible blocks on the screen.
     *
     * @param brush The graphics context to paint on
     */
    public void paint(Graphics brush) {
        for (Block block : blocks) {
            block.paint(brush);
        }
    }
    
    /**
     * Counts the number of remaining visible blocks.
     *
     * @return The number of visible blocks
     */
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
