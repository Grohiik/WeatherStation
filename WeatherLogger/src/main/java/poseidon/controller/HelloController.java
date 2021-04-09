//package poseidon.controller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * HelloController is a sample class for REST-API and its root path.
// *
// * @author Pratchaya Khansomboon
// * @version 0.0.0
// */
//@RestController
//    @RequestMapping(value = "api")
//public class HelloController {
//    @GetMapping("")
//    String root() {
//        return "Hello, World!";
//    }
//
//    @GetMapping("/hello-there")
//    String hello(@RequestParam(required = false) String name) {
//        if (name != null) return "General " + name + "!";
//        return "General Kenobi!";
//    }
//}