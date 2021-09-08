package normal_subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import publisher_autoVehicle.AutoVehiclePublisher;
import publisher_manualVehicle.ManualVehiclePublisher;


public class Activator implements BundleActivator {
	
	ServiceReference serviceReferenceAutoVehicles;
	ServiceReference serviceReferenceManualVehicles;
	
	Scanner cat = new Scanner(System.in);
	
	
	public void start(BundleContext bundleContext) throws Exception {
		
		
		serviceReferenceAutoVehicles = bundleContext.getServiceReference(AutoVehiclePublisher.class.getName());
		AutoVehiclePublisher autoVehiclePublisher = (AutoVehiclePublisher) bundleContext.getService(serviceReferenceAutoVehicles);
		
		serviceReferenceManualVehicles = bundleContext.getServiceReference(ManualVehiclePublisher.class.getName());
		ManualVehiclePublisher manualVehiclePublisher = (ManualVehiclePublisher) bundleContext.getService(serviceReferenceManualVehicles);
	
		
		System.out.println("Normal Subscriber has started.");
		
		
		System.out.println("Publishers: ");
		System.out.println("1. Auto Vehicle");
		System.out.println("2. Manual Vehicle\n");
		
		double total = 0 ;
		int category, genre, sel;
		ArrayList<String> PurchasedItems = new ArrayList<String>();
		
		try {
			
			System.out.print("Select a Category: ");
			category = cat.nextInt();
			
			if(category == 1) {
				System.out.println("\nType: ");
				System.out.println("1. Auto SUV");
				System.out.println("2. Auto Sedan");
				System.out.println("3. Auto Hacthback");
				
				
				System.out.println("\nPress O to Exit\n");
				System.out.print("Select a Type: ");
				genre = cat.nextInt();
				
				while(genre != 0) {	
					
					autoVehiclePublisher.displayAutoVehicle(genre);
					System.out.println("\nType -1 to exit from the Current Type.");
					System.out.print("\nSelect Vehicle Name: ");
					sel = cat.nextInt();
					while( sel != -1) {
						total += autoVehiclePublisher.getPrice(genre, sel);
						PurchasedItems.add(autoVehiclePublisher.getName(genre, sel));
						System.out.print("Select Vehicle Name: ");
						sel = cat.nextInt();
						
					}
					
					System.out.print("\nSelect a Type: ");
					genre = cat.nextInt();
				}
				
			}else if(category == 2) {
				
				
				System.out.println("\nType: ");
				System.out.println("1. Manual SUV");
				System.out.println("2. Manual Sedan");
				System.out.println("3. Manual Hatchback");
				
				System.out.println("\nPress O to Exit");
				System.out.print("\nSelect a Type: ");
				genre = cat.nextInt();
				while(genre != 0){	
					
					manualVehiclePublisher.displayManualVehicle(genre);
					System.out.println("\nType -1 to exit from the Current T.");
					System.out.print("\nSelect Manual Vehicle: ");
					sel = cat.nextInt();
					
					while( sel != -1) {
						total += manualVehiclePublisher.getPrice(genre, sel);
						PurchasedItems.add(manualVehiclePublisher.getName(genre, sel));
						System.out.print("Select Manual Vehicle: ");
						sel = cat.nextInt();
					}
					System.out.print("\nSelect a Type: ");
					genre = cat.nextInt();
				}
			}
			System.out.println("*************************************************************\n");
			System.out.println("Purchased Items: " + PurchasedItems);
			
			System.out.println("Total : $" + total);
			
			System.out.println("*************************************************************");
		}catch(InputMismatchException e) {
			System.out.println("Integer Should be Entered.! " + e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	public void stop(BundleContext bundleContext) throws Exception {
		
		System.out.println("Normal Subscriber Has Stopped.");
		bundleContext.ungetService(serviceReferenceAutoVehicles);
		bundleContext.ungetService(serviceReferenceManualVehicles);
	}
}
