package com.artschool;

import com.artschool.model.entity.*;
import com.artschool.model.enumeration.Audience;
import com.artschool.model.enumeration.Gender;
import com.artschool.service.course.CourseService;
import com.artschool.service.course.DateService;
import com.artschool.service.course.DayService;
import com.artschool.service.course.DisciplineService;
import com.artschool.service.gallery.PhotoService;
import com.artschool.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private static final int MAX_STUDENT_AMOUNT_PER_COURSE = 10;
    private final UserService userService;
    private final CourseService courseService;
    private final DisciplineService disciplineService;
    private final DayService dayService;
    private final DateService dateService;
    private final PhotoService photoService;

    public InitialDataLoader(UserService userService, CourseService courseService, DisciplineService disciplineService,
                             DayService dayService, DateService dateService, PhotoService photoService) {
        this.userService = userService;
        this.courseService = courseService;
        this.disciplineService = disciplineService;
        this.dayService = dayService;
        this.dateService = dateService;
        this.photoService = photoService;
    }

    @Transactional
    @Override
    public void run(String...args) throws IOException {

        createInstructors();
        createStudents();

        dayService.addDays(DayOfWeek.values());
        disciplineService.addDiscipline("CERAMICS", "DRAWING", "JEWELLERY", "MOSAICS", "PAINTING",
                "PRINTMAKING", "SCULPTURE");

        createCourses();
        enrollStudentsInCourses();

        createPhotos();
    }

    private void createStudents() throws IOException {
        byte[] studentsFromJson = Files.readAllBytes(Paths.get("src/main/resources/students.json"));
        ObjectMapper mapper = new ObjectMapper();
        Student[] students = mapper.readValue(studentsFromJson, Student[].class);
        System.out.println(Arrays.toString(students));
        for (Student student : students) {
            userService.createStudent(student);
        }
    }

    private void enrollStudentsInCourses() {
        List<Course> courses = courseService.findCourses();
        List<Student> students = userService.findStudents();
        Random random = new Random();
        for (Course course : courses) {
            for (int i = 0; i < MAX_STUDENT_AMOUNT_PER_COURSE; i++) {
                Student student = students.get(random.nextInt(students.size()));
                if (!courseService.isEnrolled(student, course) && course.getSpaces() > course.getStudents().size()) {
                    student.addPayment(new Payment(generatePaymentId(), student, course, course.getFee(),
                            LocalDate.now().minusDays(random.nextInt(15))));
                    courseService.enrollInCourse(student, course);
                }
            }
        }
    }

    private String generatePaymentId() {
        StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        for (int i = 0; i < 6; i++) {
            sb.append("0123456789");
        }
        return "PAYID-LRE" + RandomStringUtils.random(22, sb.toString());
    }

    private void createPhotos() {
        Course landscapesInOil = courseService.findCourseByName("Landscapes in Oil");
        photoService.createPhoto(new Photo("Rainy day", landscapesInOil.getStudents().iterator().next(),
                landscapesInOil));
        Course oilBasics = courseService.findCourseByName("Oil Painting Basics");
        photoService.createPhoto(new Photo("Winter mountains", oilBasics.getStudents().iterator().next(),
                oilBasics));
        Course potteryBasics = courseService.findCourseByName("Wheel Throwing Basics");
        photoService.createPhoto(new Photo("Students during pottery class", potteryBasics.getInstructor(),
                potteryBasics));
        Course mosaic = courseService.findCourseByName("Mosaic");
        photoService.createPhoto(new Photo("Varicoloured city", mosaic.getStudents().iterator().next(), mosaic));
        Course oilPortrait = courseService.findCourseByName("Portrait Painting in Oil");
        photoService.createPhoto(new Photo("Girl's portrait", oilPortrait.getStudents().iterator().next(),
                oilPortrait));
        Course pottery = courseService.findCourseByName("Wheel Throwing");
        photoService.createPhoto(new Photo("Unusual pot", pottery.getStudents().iterator().next(), pottery));
        Course jewellery = courseService.findCourseByName("Jewellery Design and Production");
        photoService.createPhoto(new Photo("Pendant drawing", jewellery.getStudents().iterator().next(),
                jewellery));
        Course pastels = courseService.findCourseByName("Exploring Pastels");
        photoService.createPhoto(new Photo("Varicoloured mountains", pastels.getStudents().iterator().next(), pastels));
        Course environments = courseService.findCourseByName("Developing Environments and Characters");
        photoService.createPhoto(new Photo("Green monster and girl", environments.getStudents().iterator().next(),
                environments));
        photoService.createPhoto(new Photo("Blue cup", potteryBasics.getStudents().iterator().next(),
                potteryBasics));
        Course portrait = courseService.findCourseByName("Drawing the Portrait");
        photoService.createPhoto(new Photo("Beautiful girl", portrait.getStudents().iterator().next(), portrait));
        Course pencilDrawing = courseService.findCourseByName("Colored Pencils Drawing");
        photoService.createPhoto(new Photo("Realistic wolf", pencilDrawing.getStudents().iterator().next(),
                pencilDrawing));
        Course paintingFundamentals = courseService.findCourseByName("Painting Fundamentals");
        photoService.createPhoto(new Photo("Kids during painting class", paintingFundamentals.getInstructor(),
                paintingFundamentals));
        photoService.createPhoto(new Photo("Seascape", paintingFundamentals.getStudents().iterator().next(),
                paintingFundamentals));
        Course watercolorPainting = courseService.findCourseByName("Exploring Watercolors");
        photoService.createPhoto(new Photo("Vase with flowers", watercolorPainting.getStudents().iterator().next(),
                watercolorPainting));
        Course beading = courseService.findCourseByName("Beading");
        photoService.createPhoto(new Photo("Varicoloured beads", beading.getStudents().iterator().next(), beading));
        Course wireWrappedRings = courseService.findCourseByName("Wire-wrapped Rings");
        photoService.createPhoto(new Photo("Wire-wrapped blue ring", wireWrappedRings.getStudents().iterator().next(),
                wireWrappedRings));
        Course penAndInkDrawing = courseService.findCourseByName("Pen and Ink Drawing");
        photoService.createPhoto(new Photo("Lion", penAndInkDrawing.getStudents().iterator().next(),
                penAndInkDrawing));
        Course drawingFundamentals = courseService.findCourseByName("Drawing Fundamentals");
        photoService.createPhoto(new Photo("Fruits", drawingFundamentals.getStudents().iterator().next(),
                drawingFundamentals));
    }

    private void createCourses() {
        List<Instructor> instructors = userService.findInstructors();
        Random random = new Random();
        courseService.createCourse(new Course("Pen and Ink Drawing",
                disciplineService.getDisciplines("DRAWING"),
                Audience.ADULTS,
                20,
                65,
                dateService.createDate(new Date(LocalDate.now().minusDays(10), LocalDate.now().plusMonths(3),
                        LocalTime.of(18, 30), LocalTime.of(21, 30))),
                dayService.getDays(DayOfWeek.MONDAY, DayOfWeek.THURSDAY),
                "This class is for beginners where we will learn the basic techniques of pen and ink " +
                        "drawing. We will be focusing on strokes, building depth, tone and value. Various exercises " +
                        "are used as well as learning how to properly use the equipment.",
                instructors.get(0)));
        courseService.createCourse(new Course("Landscapes in Oil",
                disciplineService.getDisciplines("PAINTING"),
                Audience.ADULTS,
                18,
                70,
                dateService.createDate(new Date(LocalDate.now().plusDays(10), LocalDate.now().plusMonths(3),
                        LocalTime.of(19, 0), LocalTime.of(22, 0))),
                dayService.getDays(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY),
                "Bring your supplies and come ready to paint! In this workshop, instructor will give " +
                        "group and individualized instruction including, color mixing, composition, atmospheric " +
                        "perspective and other things to bring your landscape to life.",
                instructors.get(0)));
        courseService.createCourse(new Course("Expressive Acrylic Painting",
                disciplineService.getDisciplines("PAINTING"),
                Audience.ADULTS,
                15,
                60,
                dateService.createDate(new Date(LocalDate.now().plusDays(15), LocalDate.now().plusMonths(3),
                        LocalTime.of(18, 30), LocalTime.of(22, 30))),
                dayService.getDays(DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY),
                "Learn the art of expressive painting and rethink the process of creating art. Let you " +
                        "creativity guide you.  Follow your instinct and explore different mediums while enjoying " +
                        "this unique process.",
                instructors.get(0)));
        courseService.createCourse(new Course("Wheel Throwing",
                disciplineService.getDisciplines("CERAMICS"),
                Audience.ADULTS,
                15,
                110,
                dateService.createDate(new Date(LocalDate.now().plusDays(15), LocalDate.now().plusMonths(2),
                        LocalTime.of(19, 0), LocalTime.of(22, 0))),
                dayService.getDays(DayOfWeek.THURSDAY, DayOfWeek.SATURDAY),
                "Build on your basic skills of wheel throwing and further your understanding " +
                        "of clay. Complex, food-safe and decorative forms, refinement of throwing skills, " +
                        "and advanced decorative and glazing techniques are demonstrated and discussed. " +
                        "We’ll review and critique fired results to foster the development of your " +
                        "personal style.",
                instructors.get(1)));
        courseService.createCourse(new Course("Exploring Pastels",
                disciplineService.getDisciplines("PAINTING"),
                Audience.TEENS,
                18,
                70,
                dateService.createDate(new Date(LocalDate.now().plusDays(25), LocalDate.now().plusMonths(3),
                        LocalTime.of(18, 0), LocalTime.of(21, 0))),
                dayService.getDays(DayOfWeek.MONDAY, DayOfWeek.THURSDAY),
                "Are you interested in drawing with pastels but not sure how to get started? In this " +
                        "course instructor will introduce you to pastels. This class is perfect for beginners " +
                        "looking to learn the basics of the medium in order to begin a drawing practice. By the " +
                        "end of this course you will be equipped with the know-how to start experimenting and " +
                        "drawing with pastels!",
                instructors.get(1)));
        courseService.createCourse(new Course("Wheel Throwing Basics",
                disciplineService.getDisciplines("CERAMICS"),
                Audience.TEENS,
                15,
                100,
                dateService.createDate(new Date(LocalDate.now().plusDays(10), LocalDate.now().plusMonths(3),
                        LocalTime.of(17, 30), LocalTime.of(21, 0))),
                dayService.getDays(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                "This beginning class is an introduction to wheel throwing. We’ll focus on the basic " +
                        "methods of centering clay and throwing bowls and cylinder forms such as mugs and vases. " +
                        "Use a variety of finishing and glazing techniques to produce finished works of art.",
                instructors.get(1)));
        courseService.createCourse(new Course("Mosaic",
                disciplineService.getDisciplines("MOSAICS"),
                Audience.ADULTS,
                15,
                80,
                dateService.createDate(new Date(LocalDate.now().plusDays(5), LocalDate.now().plusMonths(2),
                        LocalTime.of(19, 0), LocalTime.of(21, 0))),
                dayService.getDays(DayOfWeek.MONDAY, DayOfWeek.THURSDAY),
                "This is the perfect class for those who want to delve deeper into the world of mosaics. " +
                        "Explore various tesserae (glass, stone, marble, mirror, ceramic, found objects, etc.), " +
                        "cutting techniques, and appropriate adhesives. Specific exercises will be offered, but " +
                        "students are encouraged to create their own designs. Some basic experience with mosaics or " +
                        "previous class is required. ",
                instructors.get(2)));
        courseService.createCourse(new Course("Mosaic Basics",
                disciplineService.getDisciplines("MOSAICS"),
                Audience.TEENS,
                15,
                90,
                dateService.createDate(new Date(LocalDate.now().plusDays(15), LocalDate.now().plusMonths(3),
                        LocalTime.of(17, 0), LocalTime.of(20, 0))),
                dayService.getDays(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY),
                "This is the perfect class for those who want to experiment with and learn more about " +
                        "making mosaics. Learn how to choose, prepare, and build a base/substrate. Explore various " +
                        "tesserae (glass, stone, marble, ceramic, found objects, etc.), cutting techniques, and " +
                        "appropriate adhesives. Specific exercises will be offered, but students are also encouraged " +
                        "to create their own designs.",
                instructors.get(2)));
        courseService.createCourse(new Course("Sculpturing the Figure",
                disciplineService.getDisciplines("SCULPTURE"),
                Audience.ADULTS,
                10,
                130,
                dateService.createDate(new Date(LocalDate.now().plusDays(15), LocalDate.now().plusMonths(3),
                        LocalTime.of(19, 0), LocalTime.of(22, 0))),
                dayService.getDays(DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY),
                "Learn to sculpt the human figure while exploring different types of sculpting media. " +
                        "Practice the basic proportions of the human form and create a sculpture that you can " +
                        "display inside or outside. Grab some clay and let’s have some fun! ",
                instructors.get(2)));
        courseService.createCourse(new Course("Jewellery Design and Production",
                disciplineService.getDisciplines("JEWELLERY"),
                Audience.ADULTS,
                15,
                150,
                dateService.createDate(new Date(LocalDate.now().plusDays(5), LocalDate.now().plusMonths(3),
                        LocalTime.of(18, 30), LocalTime.of(22, 0))),
                dayService.getDays(DayOfWeek.MONDAY, DayOfWeek.THURSDAY),
                "Explore lessons in: jewelry design, wax modeling, construction, lost wax " +
                        "casting, stone/found object setting, polishing and finish techniques, while using " +
                        "a variety of hand and power tools.",
                instructors.get(3)));
        courseService.createCourse(new Course("Beading",
                disciplineService.getDisciplines("JEWELLERY"),
                Audience.TEENS,
                15,
                90,
                dateService.createDate(new Date(LocalDate.now().plusDays(10), LocalDate.now().plusMonths(2),
                        LocalTime.of(16, 0), LocalTime.of(22, 0))),
                dayService.getDays(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                "Learn the techniques the professionals use to design, create, and finish " +
                        "earrings, necklaces, and bracelets. Create unique pieces to enhance your wardrobe, " +
                        "give as gifts, or create jewelry to sell in this fun, fast-paced class. You’ll " +
                        "also learn techniques to repair vintage favorites to be worn again or revamp them " +
                        "into something new.",
                instructors.get(3)));
        courseService.createCourse(new Course("Portrait Painting in Oil",
                disciplineService.getDisciplines("PAINTING"),
                Audience.ADULTS,
                12,
                100,
                dateService.createDate(new Date(LocalDate.now().plusDays(5), LocalDate.now().plusMonths(3),
                        LocalTime.of(12, 0), LocalTime.of(16, 0))),
                dayService.getDays(DayOfWeek.SATURDAY),
                "There are many criteria that go into creating a successful portrait. " +
                        "A great portrait feels alive, evokes emotion, and captures the viewer through " +
                        "the beauty of its maker’s compositional and aesthetic choices. We’ll discuss " +
                        "creating a concept, formulating good composition, and designing of marks, strokes, " +
                        "color, and light. We’ll also break down the formal process of painting a portrait " +
                        "into its most basic parts: drawing, value, and color. Some experience in oil.",
                instructors.get(3)));
        courseService.createCourse(new Course("Printmaking",
                disciplineService.getDisciplines("PRINTMAKING"),
                Audience.KIDS,
                10,
                50,
                dateService.createDate(new Date(LocalDate.now().plusDays(10), LocalDate.now().plusMonths(3),
                        LocalTime.of(16, 0), LocalTime.of(17, 30))),
                dayService.getDays(DayOfWeek.TUESDAY),
                "In this class, explore your creative side and discover various methods " +
                        "of printmaking to include monoprints and monotypes, the creativity of single " +
                        "use and multiple use plates.",
                instructors.get(3)));
        courseService.createCourse(new Course("Ink Drawing Basics",
                disciplineService.getDisciplines("PAINTING"),
                Audience.TEENS,
                15,
                120,
                dateService.createDate(new Date(LocalDate.now().plusDays(15), LocalDate.now().plusMonths(4),
                        LocalTime.of(17, 0), LocalTime.of(20, 0))),
                dayService.getDays(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY),
                "Drawing is a skill you can learn and this class is designed especially for " +
                        "beginners. Using black and colored ball point pens, markers, and inks, you will " +
                        "experiment with the basics of creating light and dark values, seeing shapes, " +
                        "drawing three-dimensionally, and creating texture, in order to draw creative " +
                        "works of art. Art is a unique form of communication and drawing is a great " +
                        "foundation for experimenting with all other art media. Each class will include " +
                        "a variety of basic exercises that will enable you to create one or more finished " +
                        "drawings.",
                instructors.get(4)));
        courseService.createCourse(new Course("Developing Environments and Characters",
                disciplineService.getDisciplines("PAINTING"),
                Audience.TEENS,
                20,
                45,
                dateService.createDate(new Date(LocalDate.now().minusDays(5), LocalDate.now().plusMonths(2),
                        LocalTime.of(17, 0), LocalTime.of(20, 0))),
                dayService.getDays(DayOfWeek.MONDAY, DayOfWeek.THURSDAY),
                "In this course, you will learn about the components of developing a visual narrative " +
                        "and learn about best practices for creating more believable characters, authentic worlds " +
                        "and compelling, dynamic visuals that tell a story. Instructor will also guide viewers " +
                        "through developing compositions, creating depth of field and merging real and imaginary " +
                        "worlds. The course will come to life through real-world projects from masters of the " +
                        "industry.",
                instructors.get(4)));
        courseService.createCourse(new Course("Wire-wrapped Rings",
                disciplineService.getDisciplines("JEWELLERY"),
                Audience.TEENS,
                15,
                115,
                dateService.createDate(new Date(LocalDate.now().plusDays(20), LocalDate.now().plusMonths(2),
                        LocalTime.of(16, 0), LocalTime.of(18, 0))),
                dayService.getDays(DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY),
                "Create unique, one-of-a-kind rings using only beads and wire. The rings will " +
                        "be customized with a variety of shapes, sizes, and colors of beads as well as a " +
                        "selection of colors of 20-gauge wire. Feel free to bring your own beads or choose " +
                        "from a variety of beads provided by the instructor.",
                instructors.get(4)));
        courseService.createCourse(new Course("Exploring Watercolors",
                disciplineService.getDisciplines("DRAWING"),
                Audience.ADULTS,
                15,
                70,
                dateService.createDate(new Date(LocalDate.now().plusDays(10), LocalDate.now().plusMonths(3),
                        LocalTime.of(19, 0), LocalTime.of(22, 0))),
                dayService.getDays(DayOfWeek.MONDAY, DayOfWeek.THURSDAY),
                "Discover the joy of the unrivaled and endless possibilities of watercolor. " +
                        "You do not need any natural ability, just the desire to learn. Build a repertoire " +
                        "of skills from choosing materials, understanding the art elements and " +
                        "incorporating traditional and experimental techniques for limitless possibilities. " +
                        "You’ll be addicted.",
                instructors.get(5)));
        courseService.createCourse(new Course("Drawing the Portrait",
                disciplineService.getDisciplines("DRAWING"),
                Audience.ADULTS,
                10,
                80,
                dateService.createDate(new Date(LocalDate.now().plusDays(25), LocalDate.now().plusMonths(3),
                        LocalTime.of(12, 0), LocalTime.of(16, 0))),
                dayService.getDays(DayOfWeek.SATURDAY),
                "We’ll get started by concentrating on proper placement of shapes, " +
                        "use of proportions, and the overall design. The first few classes will focus " +
                        "on working from a photograph as reference material. Discussions will be centered " +
                        "on the materials used for drawing while considering proportions, composition, " +
                        "space, movement, balance, direction, and rhythm.",
                instructors.get(5)));
        courseService.createCourse(new Course("Colored Pencils Drawing",
                disciplineService.getDisciplines("DRAWING"),
                Audience.TEENS,
                18,
                60,
                dateService.createDate(new Date(LocalDate.now().plusDays(5), LocalDate.now().plusMonths(2),
                        LocalTime.of(17, 0), LocalTime.of(20, 0))),
                dayService.getDays(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY),
                "Learn to use color in your art while practicing the basics of drawing. You will " +
                        "study texture, composition, and perspective, as well as basic color theory, color mixing, " +
                        "burnishing, layering, and shading. Grab your pencils and sketch book, and let’s draw!",
                instructors.get(5)));
        courseService.createCourse(new Course("Drawing Fundamentals",
                disciplineService.getDisciplines("DRAWING"),
                Audience.KIDS,
                10,
                120,
                dateService.createDate(new Date(LocalDate.now().plusDays(15), LocalDate.now().plusMonths(4),
                        LocalTime.of(16, 0), LocalTime.of(18, 0))),
                dayService.getDays(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                "Learn basic drawing techniques through a variety of lessons and exercises as you " +
                        "discover the fundamentals of drawing in this fun and informative course. Projects " +
                        "will include value studies, still life, landscape, figure, and contour drawings, using " +
                        "charcoal, pencil, ink, and colored mediums.",
                instructors.get(6)));
        courseService.createCourse(new Course("Painting Fundamentals",
                disciplineService.getDisciplines("PAINTING"),
                Audience.KIDS,
                10,
                110,
                dateService.createDate(new Date(LocalDate.now().plusDays(10), LocalDate.now().plusMonths(5),
                        LocalTime.of(12, 0), LocalTime.of(14, 30))),
                dayService.getDays(DayOfWeek.SATURDAY),
                "Discover the wonderful world of painting as you experiment with a variety of oil " +
                        "media and enjoy plenty of individualized instruction. Focus on mixing and applying colors, " +
                        "textures, and techniques.",
                instructors.get(6)));
        courseService.createCourse(new Course("Oil Painting Basics",
                disciplineService.getDisciplines("DRAWING"),
                Audience.TEENS,
                15,
                100,
                dateService.createDate(new Date(LocalDate.now().plusDays(20), LocalDate.now().plusMonths(4),
                        LocalTime.of(19, 0), LocalTime.of(22, 0))),
                dayService.getDays(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
                "Discover the wonderful world of oil painting as you experiment with a variety of oil " +
                        "media and enjoy plenty of individualized instruction. Focus on mixing and applying colors, " +
                        "textures, and techniques. Learn how to paint landscapes and still life paintings in this " +
                        "fun, relaxed environment.",
                instructors.get(6)));
    }

    private void createInstructors() {
        userService.createInstructor(new Instructor("Emily", "Horton",
                Gender.FEMALE,
                "847508836472", "emily@gmail.com",
                "$2a$10$Xg.JmfKBVKJNR.GoM8nX8.POT3KJW5tE75ngItbj.s6vGTGDtyuXS",
                "Emily Horton received her degree in Fine Arts from Academy of Art University in San Francisco " +
                        "in 2008. She is experienced in various fields including drawing and painting, sculpting, " +
                        "2d and 3d animation and illustration. As a teacher, she is confident with all of the age " +
                        "groups from toddlers to adults, providing personal approach and fun learning environment " +
                        "in her classes."));
        userService.createInstructor(new Instructor("Mark", "Reinold",
                Gender.MALE,
                "385978463742", "mark@gmail.com",
                "$2a$10$Xg.JmfKBVKJNR.GoM8nX8.POT3KJW5tE75ngItbj.s6vGTGDtyuXS",
                "Mark Reinold is a Florida born San Francisico illustrator specializing in traditional media " +
                        "like watercolor, ink, charcoal and acrylics. He graduated with a BFA from the Academy of " +
                        "Art University and now works as an instructor and freelance illustrator. Having taken an " +
                        "interest in art since a child, Mark pulls influences from classical art and the masters of " +
                        "20th century American illustration, as well as influence from contemporary illustrators " +
                        "and concept artists. His work spans from classical studies and demonstrations of realism " +
                        "to highly stylized storytelling and character illustrations."));
        userService.createInstructor(new Instructor("Livia", "Donnellan", Gender.FEMALE,
                "394803345662", "ldonnellan@yahoo.com",
                "$2a$10$Xg.JmfKBVKJNR.GoM8nX8.POT3KJW5tE75ngItbj.s6vGTGDtyuXS",
                "Livia Donnellan is a young artist with a passion for character design and illustration. " +
                        "Livia enjoys weaving in a variety of concepts in a way that is pleasing to look at and " +
                        "draws the eye in. The thing she considers most important in art is the amount of life an " +
                        "image has, and thus consistently pumps out pieces that capitalize on mood and motion. " +
                        "She draws inspiration partly from the world around her and partly from her imagination, " +
                        "and adores creating works that blend the two together."));
        userService.createInstructor(new Instructor("Octavia", "Halm", Gender.FEMALE,
                "394803345662", "ohalm@gmail.com",
                "$2a$10$Xg.JmfKBVKJNR.GoM8nX8.POT3KJW5tE75ngItbj.s6vGTGDtyuXS",
                "Octavia Halm has had a lifelong love of the arts. Originally from Cincinnati, Ohio, " +
                        "Octavia attended Miami University in Oxford, Ohio, and graduated with a degree in fine " +
                        "arts. Her teaching experience spans the last 15 years all around the United States " +
                        "and overseas. As a military spouse, Catherine has had the opportunity to teach K-12 " +
                        "in Ohio, Virginia, Maryland and Okinawa, Japan. Away from teaching Octavia enjoys art " +
                        "projects that range from printmaking to ceramics. Beyond artistic pursuits she enjoys time " +
                        "with her family, going to dog parks with her new puppy and volunteering at her children’s" +
                        " school."));
        userService.createInstructor(new Instructor("Luci", "Thonason", Gender.FEMALE,
                "394803345662", "lthonason@yahoo.com",
                "$2a$10$Xg.JmfKBVKJNR.GoM8nX8.POT3KJW5tE75ngItbj.s6vGTGDtyuXS",
                "With work experience in the fields of visual merchandising, advertising, " +
                        "college design instruction, and art gallery work, Luci Thonason has always managed to " +
                        "keep my hand in the creative fields. Luci has never stopped moving forward with personal " +
                        "creative endeavors by taking classes and having tried just about every design and " +
                        "fine-art medium. Luci like the challenge of trying to integrate various materials into a " +
                        "visual statement, always a fun creative exercise. Some of my passions include: art, " +
                        "music, writing, travel, history, yoga, and roller skating."));

        userService.createInstructor(new Instructor("Lesley", "Mellam", Gender.FEMALE,
                "394803345662", "lmellam@hotmail.com",
                "$2a$10$Xg.JmfKBVKJNR.GoM8nX8.POT3KJW5tE75ngItbj.s6vGTGDtyuXS",
                "Lesley is an award winning chalk artist, album cover artist, former gallery owner, " +
                        "and left handed bassist for the Common Link Trio! Lesley teaches a variety of media to " +
                        "students of all ages and loves working with them to bring out their own unique artistic " +
                        "style! She is amazed at what kids are able to do in a short period of time. When not " +
                        "making art or music, Lesley can be found at her favorite place on earth…the beach! " +
                        "It is her passion to help rid the local beaches of plastic litter, which unfortunately, " +
                        "is a huge problem. It is her goal to take this plastic refuse and make art from it. " +
                        "She hopes other artists will follow suit!"));

        userService.createInstructor(new Instructor("Horacio", "Cuer", Gender.MALE,
                "394803345662", "hcuer@hotmail.com",
                "$2a$10$Xg.JmfKBVKJNR.GoM8nX8.POT3KJW5tE75ngItbj.s6vGTGDtyuXS",
                "Allen Montague is a native North Carolinian who began his creative career at age 13, " +
                        "when he designed and received his first patent. He started his own clothing accessory " +
                        "line. This interest in color and design was an easy segues into paints and canvas. " +
                        "He began his art career and has never looked back. Two yeas after becoming a self-taught " +
                        "fine art painter his work was being catalogued by the International Art Auction House of " +
                        "Sotheby’s in New York. Soon after, Allen began to produce not only fine art originals, " +
                        "but published hundreds of limited and open edition prints. To meet the demand for his work, " +
                        "he opened several art galleries. For the next seventeen plus years Allen painted, " +
                        "published, and continued to design and invent. Allen taught himself to finger paint in the " +
                        "style of French impressionists. Over the years, Allen has shared his talents, love of " +
                        "people, and art where viewers could actually paint along with him, no matter what their " +
                        "experience level."));
    }

}
