<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Campus Sync - Smart College Management System</title>
    
    <!-- Added Font Awesome icons library for professional icon support -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #ffffff;
            min-height: 100vh;
            overflow-x: hidden;
        }

        /* Hero Section: Split layout with college campus image on the left and content on the right */
        .hero {
            display: grid;
            grid-template-columns: 1fr 1fr;
            align-items: center;
            min-height: 100vh;
            gap: 0;
        }

        /* College Campus Image Section */
        .hero__image-container {
            position: relative;
            width: 100%;
            height: 100vh;
            overflow: hidden;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        .hero__image {
            width: 100%;
            height: 100%;
            object-fit: cover;
            object-position: center;
            display: block;
        }

        /* Overlay gradient for better text readability if needed */
        .hero__image-overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, rgba(102, 126, 234, 0.15) 0%, rgba(118, 75, 162, 0.15) 100%);
            pointer-events: none;
        }

        /* Content Section on the Right */
        .hero__content {
            padding: 60px 50px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            height: 100vh;
            background: #ffffff;
        }

        .hero__badge {
            display: inline-block;
            background: rgba(102, 126, 234, 0.1);
            color: #667eea;
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-bottom: 24px;
            width: fit-content;
        }

        .hero__title {
            font-size: 52px;
            font-weight: 800;
            color: #1a202c;
            line-height: 1.2;
            margin-bottom: 20px;
            letter-spacing: -0.5px;
        }

        .hero__description {
            font-size: 18px;
            color: #718096;
            line-height: 1.8;
            margin-bottom: 40px;
            max-width: 450px;
        }

        .hero__features {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 24px;
            margin-bottom: 40px;
            max-width: 450px;
        }

        /* Updated feature card styling to work with Font Awesome icons */
        .feature {
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        /* New icon styling for Font Awesome icons */
        .feature__icon {
            font-size: 32px;
            color: #667eea;
            width: 40px;
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .feature__title {
            font-size: 12px;
            font-weight: 700;
            color: #718096;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .feature__value {
            font-size: 20px;
            font-weight: 800;
            color: #667eea;
            line-height: 1.2;
        }

        .hero__cta-group {
            display: flex;
            gap: 16px;
            flex-wrap: wrap;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            padding: 16px 32px;
            border-radius: 12px;
            font-size: 15px;
            font-weight: 600;
            transition: all 0.3s ease;
            border: 2px solid transparent;
            cursor: pointer;
        }

        .btn--primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            box-shadow: 0 8px 24px rgba(102, 126, 234, 0.3);
        }

        .btn--primary:hover {
            transform: translateY(-3px);
            box-shadow: 0 12px 32px rgba(102, 126, 234, 0.4);
        }

        .btn--secondary {
            background: white;
            color: #667eea;
            border: 2px solid #e2e8f0;
        }

        .btn--secondary:hover {
            border-color: #667eea;
            background: rgba(102, 126, 234, 0.05);
        }

        /* Responsive Design */
        @media (max-width: 1024px) {
            .hero {
                grid-template-columns: 1fr;
                min-height: auto;
            }

            .hero__image-container {
                height: 500px;
                min-height: 400px;
            }

            .hero__content {
                height: auto;
                padding: 50px 40px;
            }

            .hero__title {
                font-size: 42px;
            }

            .hero__features {
                max-width: 100%;
            }
        }

        @media (max-width: 768px) {
            .hero__content {
                padding: 40px 24px;
            }

            .hero__title {
                font-size: 36px;
            }

            .hero__description {
                font-size: 16px;
            }

            .hero__features {
                grid-template-columns: 1fr;
                max-width: 100%;
            }

            .hero__cta-group {
                flex-direction: column;
            }

            .btn {
                width: 100%;
            }
        }

        /* Animation for hero elements */
        @keyframes slideInLeft {
            from {
                opacity: 0;
                transform: translateX(-30px);
            }
            to {
                opacity: 1;
                transform: translateX(0);
            }
        }

        @keyframes slideInRight {
            from {
                opacity: 0;
                transform: translateX(30px);
            }
            to {
                opacity: 1;
                transform: translateX(0);
            }
        }

        .hero__image-container {
            animation: slideInLeft 0.7s ease-out;
        }

        .hero__content {
            animation: slideInRight 0.7s ease-out 0.1s both;
        }
    </style>
</head>

<body>
    <!-- Hero Section with College Campus Image -->
    <section class="hero">
        <!-- Left: College Campus Image -->
        <div class="hero__image-container">
            <img 
                src="https://images.pexels.com/photos/159740/library-la-trobe-study-students-159740.jpeg?height=1080&width=1080"
                alt="Modern college campus with students"
                class="hero__image"
                loading="lazy"
            >
            <div class="hero__image-overlay"></div>
        </div>

        <!-- Right: Content Section -->
        <div class="hero__content">
            <span class="hero__badge">Smart Campus Management</span>
            
            <h1 class="hero__title">Campus Sync</h1>
            
            <p class="hero__description">
                Streamline college operations with our comprehensive management system. 
                Connect administrators, faculty, and students on one unified platform.
            </p>

            <!-- Key Features Grid -->
            <!-- Replaced emoji icons with Font Awesome icons for professional appearance -->
            <div class="hero__features">
                <div class="feature">
                    <div class="feature__icon">
                        <i class="fas fa-chart-line"></i>
                    </div>
                    <span class="feature__title">Real-time</span>
                    <span class="feature__value">Dashboard</span>
                </div>
                <div class="feature">
                    <div class="feature__icon">
                        <i class="fas fa-users"></i>
                    </div>
                    <span class="feature__title">Multi-user</span>
                    <span class="feature__value">Access</span>
                </div>
                <div class="feature">
                    <div class="feature__icon">
                        <i class="fas fa-clipboard-check"></i>
                    </div>
                    <span class="feature__title">Attendance</span>
                    <span class="feature__value">Tracking</span>
                </div>
                <div class="feature">
                    <div class="feature__icon">
                        <i class="fas fa-star"></i>
                    </div>
                    <span class="feature__title">Grade</span>
                    <span class="feature__value">Management</span>
                </div>
            </div>

            <!-- Call-to-Action Buttons -->
            <div class="hero__cta-group">
                <a href="login.jsp" class="btn btn--primary">Get Started Now</a>
                <a href="#features" class="btn btn--secondary">Learn More</a>
            </div>
        </div>
    </section>
</body>

</html>
