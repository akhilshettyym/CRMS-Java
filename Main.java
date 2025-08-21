import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {

    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }
}

class Customer {

    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {

    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent !!");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("==============================");
        System.out.println("WELCOME TO CAR RENTAL SYSTEM");
        System.out.println("==============================");
        while (true) {
            System.out.println("      How can I help you");
            System.out.println("------------------------------");
            System.out.println("1. Rent a car ?");
            System.out.println("2. Return rented car ?");
            System.out.println("3. EXIT");

            System.out.println("------------------------------");
            System.out.print("Enter your choice : ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline...

            if (choice == 1) {
                System.out.println("------------------------------");
                System.out.println("   You chose to rent a Car");
                System.out.println("==============================");

                boolean hasAvailable = false;
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        hasAvailable = true;
                        break;
                    }
                }

                if (!hasAvailable) {
                    System.out.println("Sorry, no cars available rn.\n      Please try later.");
                    System.out.println("------------------------------");
                    continue;
                }

                System.out.print("Enter your name : ");
                String customerName = sc.nextLine();

                System.out.println("------------------------------");
                System.out.println("      Available cars : ");
                System.out.println("------------------------------");

                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + "  - " + car.getBrand() + "  - " + car.getModel());
                    }
                }

                System.out.println("------------------------------");
                System.out.print("Enter the ID of the car : ");
                String carId = sc.nextLine();

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar == null) {
                    System.out.println("Invalid car selection or car not available!");
                    continue;
                }

                System.out.print("Days of rental : ");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                double totalPrice = selectedCar.calculatePrice(rentalDays);
                System.out.println("==============================");
                System.out.println("    Rental Information");
                System.out.println("==============================");
                System.out.println("Customer ID    : " + newCustomer.getCustomerId());
                System.out.println("Customer Name  : " + newCustomer.getName());
                System.out.println("Car            : " + selectedCar.getBrand() + " " + selectedCar.getModel());
                System.out.println("Rental Days    : " + rentalDays);
                System.out.printf("Total Price    : $%.2f%n", totalPrice);

                System.out.println("------------------------------");
                System.out.print("Confirm rental (Y/N) : ");
                String confirm = sc.nextLine();
                System.out.println("------------------------------");

                if (confirm.equalsIgnoreCase("Y")) {
                    rentCar(selectedCar, newCustomer, rentalDays);
                    System.out.println("\t  CONGRATS");
                    System.out.println("   Car rented successfully");
                    System.out.println("==============================");
                } else {
                    System.out.println("Rental Canceled");
                }
            } else if (choice == 2) {
                System.out.println("------------------------------");
                System.out.println("  You chose to return a Car");
                System.out.println("==============================");
                System.out.print("Enter the Id of the Car : ");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }
                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("------------------------------");
                        System.out.println("Car returned successfully by \n\t" + customer.getName());
                        System.out.println("==============================");
                    } else {
                        System.out.println("------------------------------");
                        System.out.println("   Car was not rented \n   rental info is missing");
                        System.out.println("------------------------------");
                    }
                } else {
                    System.out.println("------------------------------");
                    System.out.println("     Invalid car Id \n    car is not rented..!!");
                    System.out.println("------------------------------");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("------------------------------");
                System.out.println("\t  Invalid choice !! \n please enter a valid option...");
                System.out.println("------------------------------");
            }

        }
        System.out.println("------------------------------");
        System.out.println(" Thank you for using CRMS ! \n \t- Akhil");
        System.out.println("==============================");
        sc.close();
    }
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Camry", 60.0);
        rentalSystem.addCar(car1);
        Car car2 = new Car("C002", "Toyota", "LC", 500.0);
        rentalSystem.addCar(car2);
        Car car3 = new Car("C003", "Toyota", "Fortuner", 200.0);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}