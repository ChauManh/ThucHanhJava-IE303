import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class BTTH3Swing {
    private final List<Product> products = buildProducts();
    private final List<ProductCard> cards = new ArrayList<ProductCard>();
    private final ProductDetailPanel detailPanel = new ProductDetailPanel();
    private int selectedIndex = -1;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BTTH3Swing().createAndShowUi();
            }
        });
    }

    private void createAndShowUi() {
        setSystemLookAndFeel();

        JFrame frame = new JFrame("BTTH 3 - Product Showcase");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 650);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(16, 0));
        root.setBackground(new Color(236, 236, 236));
        root.setBorder(new EmptyBorder(14, 14, 14, 14));
        frame.setContentPane(root);

        detailPanel.setPreferredSize(new Dimension(300, 0));
        root.add(detailPanel, BorderLayout.WEST);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new java.awt.GridLayout(0, 4, 10, 10));
        gridPanel.setOpaque(false);

        for (int i = 0; i < products.size(); i++) {
            final int index = i;
            ProductCard card = new ProductCard(products.get(i));
            installClickHandler(card, new Runnable() {
                @Override
                public void run() {
                    selectProduct(index, true);
                }
            });
            cards.add(card);
            gridPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        root.add(scrollPane, BorderLayout.CENTER);

        selectProduct(0, false);
        frame.setVisible(true);
    }

    private void selectProduct(int index, boolean animate) {
        if (index < 0 || index >= products.size()) {
            return;
        }
        selectedIndex = index;
        detailPanel.showProduct(products.get(index), animate);
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setSelected(i == selectedIndex);
        }
    }

    private static void installClickHandler(Component component, final Runnable handler) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handler.run();
            }
        });
        if (component instanceof Container) {
            Component[] children = ((Container) component).getComponents();
            for (Component child : children) {
                installClickHandler(child, handler);
            }
        }
    }

    private static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Keep default look and feel when system theme is unavailable.
        }
    }

    private static List<Product> buildProducts() {
        List<Product> data = new ArrayList<Product>();
        data.add(new Product("4DFWD PULSE SHOES", "This product is excluded from all promotional offers.",
                "Adidas", 160.00, "This product is excluded from all promotional discounts and offers.", loadImage("img1.png")));
        data.add(new Product("FORUM MID SHOES", "This product is excluded from all promotional offers.",
                "Adidas", 100.00, "This product is excluded from all promotional discounts and offers.", loadImage("img2.png")));
        data.add(new Product("SUPERNOVA SHOES", "NMD City Stock 2",
                "Adidas", 150.00, "Lightweight performance with everyday comfort for city runs.", loadImage("img3.png")));
        data.add(new Product("Adidas", "NMD City Stock 2",
                "Adidas", 160.00, "Engineered with responsive cushioning and breathable mesh upper.", loadImage("img4.png")));
        data.add(new Product("Adidas", "NMD City Stock 2",
                "Adidas", 120.00, "Reflective texture and adaptive fit for all-day wear.", loadImage("img5.png")));
        data.add(new Product("4DFWD PULSE SHOES", "This product is excluded from all promotional offers.",
                "Adidas", 160.00, "This product is excluded from all promotional discounts and offers.", loadImage("img6.png")));
        data.add(new Product("4DFWD PULSE SHOES", "This product is excluded from all promotional offers.",
                "Adidas", 160.00, "This product is excluded from all promotional discounts and offers.", loadImage("img1.png")));
        data.add(new Product("FORUM MID SHOES", "This product is excluded from all promotional offers.",
                "Adidas", 100.00, "This product is excluded from all promotional discounts and offers.", loadImage("img2.png")));
        return data;
    }

    private static BufferedImage loadImage(String fileName) {
        List<File> candidates = new ArrayList<File>();
        candidates.add(new File(fileName));
        candidates.add(new File(System.getProperty("user.dir"), fileName));

        for (File file : candidates) {
            if (file.exists()) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    if (image != null) {
                        return image;
                    }
                } catch (IOException ignored) {
                    // Fall through to placeholder generation.
                }
            }
        }
        return createPlaceholder(fileName);
    }

    private static BufferedImage createPlaceholder(String text) {
        BufferedImage image = new BufferedImage(600, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(new Color(225, 225, 225));
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2.setColor(new Color(120, 120, 120));
        g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
        g2.drawString("Missing image:", 30, 170);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        g2.drawString(text, 30, 210);
        g2.dispose();
        return image;
    }

    private static BufferedImage fitImage(BufferedImage source, int width, int height) {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = result.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double scale = Math.min((double) width / source.getWidth(), (double) height / source.getHeight());
        int drawWidth = (int) Math.round(source.getWidth() * scale);
        int drawHeight = (int) Math.round(source.getHeight() * scale);
        int x = (width - drawWidth) / 2;
        int y = (height - drawHeight) / 2;
        g2.drawImage(source, x, y, drawWidth, drawHeight, null);
        g2.dispose();
        return result;
    }

    private static String ellipsis(String text, int maxLength) {
        if (text == null || text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, Math.max(0, maxLength - 3)).trim() + "...";
    }

    private static final class Product {
        private final String name;
        private final String subtitle;
        private final String brand;
        private final double price;
        private final String description;
        private final BufferedImage image;

        private Product(String name, String subtitle, String brand, double price, String description, BufferedImage image) {
            this.name = name;
            this.subtitle = subtitle;
            this.brand = brand;
            this.price = price;
            this.description = description;
            this.image = image;
        }
    }

    private static final class ProductCard extends JPanel {
        private static final DecimalFormat MONEY = new DecimalFormat("$#,##0.00");
        private boolean selected;

        private ProductCard(Product product) {
            setOpaque(false);
            setPreferredSize(new Dimension(210, 240));
            setLayout(new BorderLayout(0, 8));
            setBorder(new EmptyBorder(8, 8, 8, 8));

            JPanel top = new JPanel();
            top.setOpaque(false);
            top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

            JLabel title = new JLabel(ellipsis(product.name, 16));
            title.setFont(new Font("Segoe UI", Font.BOLD, 31));
            title.setForeground(new Color(70, 70, 70));
            top.add(title);

            JLabel subtitle = new JLabel(ellipsis(product.subtitle, 28));
            subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 24));
            subtitle.setForeground(new Color(150, 150, 150));
            top.add(subtitle);

            add(top, BorderLayout.NORTH);

            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setIcon(new javax.swing.ImageIcon(fitImage(product.image, 165, 95)));
            add(imageLabel, BorderLayout.CENTER);

            JPanel footer = new JPanel(new BorderLayout());
            footer.setOpaque(false);

            JLabel brandLabel = new JLabel(product.brand);
            brandLabel.setFont(new Font("Segoe UI", Font.PLAIN, 23));
            brandLabel.setForeground(new Color(80, 80, 80));
            footer.add(brandLabel, BorderLayout.WEST);

            JLabel priceLabel = new JLabel(MONEY.format(product.price));
            priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
            priceLabel.setForeground(new Color(70, 70, 70));
            footer.add(priceLabel, BorderLayout.EAST);

            add(footer, BorderLayout.SOUTH);
        }

        private void setSelected(boolean selected) {
            this.selected = selected;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(232, 232, 232));
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(selected ? new Color(98, 153, 255) : new Color(232, 232, 232));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
            g2.dispose();
        }
    }

    private static final class ProductDetailPanel extends JPanel {
        private static final DecimalFormat MONEY = new DecimalFormat("$#,##0.00");
        private final CrossFadeImagePanel imagePanel = new CrossFadeImagePanel();
        private final JLabel titleLabel = new JLabel();
        private final JLabel priceLabel = new JLabel();
        private final JLabel brandLabel = new JLabel();
        private final JLabel descriptionLabel = new JLabel();

        private ProductDetailPanel() {
            setOpaque(false);
            setLayout(new BorderLayout(0, 10));

            imagePanel.setPreferredSize(new Dimension(280, 210));
            add(imagePanel, BorderLayout.NORTH);

            JPanel info = new JPanel();
            info.setOpaque(false);
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

            JSeparator separator = new JSeparator();
            separator.setForeground(new Color(170, 170, 170));
            separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            info.add(separator);
            info.add(Box.createVerticalStrut(12));

            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
            titleLabel.setForeground(new Color(70, 70, 70));
            info.add(titleLabel);

            info.add(Box.createVerticalStrut(6));
            priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
            priceLabel.setForeground(new Color(70, 70, 70));
            info.add(priceLabel);

            info.add(Box.createVerticalStrut(4));
            brandLabel.setFont(new Font("Segoe UI", Font.PLAIN, 30));
            brandLabel.setForeground(new Color(80, 80, 80));
            info.add(brandLabel);

            info.add(Box.createVerticalStrut(6));
            descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));
            descriptionLabel.setForeground(new Color(150, 150, 150));
            info.add(descriptionLabel);

            add(info, BorderLayout.CENTER);
        }

        private void showProduct(Product product, boolean animate) {
            titleLabel.setText(product.name);
            priceLabel.setText(MONEY.format(product.price));
            brandLabel.setText(product.brand);
            descriptionLabel.setText("<html><div style='width:260px'>" + product.description + "</div></html>");
            imagePanel.setImage(product.image, animate);
        }
    }

    private static final class CrossFadeImagePanel extends JPanel {
        private BufferedImage currentImage;
        private BufferedImage nextImage;
        private float progress = 1.0f;
        private Timer animationTimer;

        private CrossFadeImagePanel() {
            setOpaque(false);
        }

        private void setImage(BufferedImage image, boolean animate) {
            if (image == null) {
                return;
            }
            if (!animate || currentImage == null) {
                currentImage = image;
                nextImage = null;
                progress = 1.0f;
                repaint();
                return;
            }
            nextImage = image;
            progress = 0.0f;
            if (animationTimer != null && animationTimer.isRunning()) {
                animationTimer.stop();
            }
            animationTimer = new Timer(16, e -> {
                progress += 0.08f;
                if (progress >= 1.0f) {
                    progress = 1.0f;
                    currentImage = nextImage;
                    nextImage = null;
                    animationTimer.stop();
                }
                repaint();
            });
            animationTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (currentImage == null) {
                return;
            }

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (nextImage == null) {
                drawImage(g2, currentImage, 1.0f);
            } else {
                drawImage(g2, currentImage, 1.0f - progress);
                drawImage(g2, nextImage, progress);
            }
            g2.dispose();
        }

        private void drawImage(Graphics2D g2, BufferedImage image, float alpha) {
            if (alpha <= 0f) {
                return;
            }
            int w = getWidth();
            int h = getHeight();
            double scale = Math.min((double) w / image.getWidth(), (double) h / image.getHeight());
            int drawW = (int) Math.round(image.getWidth() * scale);
            int drawH = (int) Math.round(image.getHeight() * scale);
            int x = (w - drawW) / 2;
            int y = (h - drawH) / 2;

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.drawImage(image, x, y, drawW, drawH, null);
            g2.setComposite(AlphaComposite.SrcOver);
        }
    }
}
