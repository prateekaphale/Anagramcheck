import mysql.connector 
from tkinter import *
from datetime import datetime
from getpass import getpass
import bcrypt

db = mysql.connector.connect(host="localhost",user="root",password="Prateek@123",auth_plugin='mysql_native_password',database = "Allmighty")
mycursor = db.cursor()
#mycursor.execute("ALTER TABLE Login_authenticate MODIFY password BINARY(60)")
#mycursor.execute("CREATE TABLE Login_info(Username varchar(100) PRIMARY KEY NOT NULL,password varchar(100),created datetime)")
#mycursor.execute("CREATE TABLE registerinfo(Username varchar(100) PRIMARY KEY NOT NULL,password varchar(100),created datetime, gender ENUM('M','F','O'))")
#newone mycursor.execute("CREATE TABLE Login_authenticate(Username varchar(50) PRIMARY KEY NOT NULL,password varchar(50))")
#new one mycursor.execute("CREATE TABLE Applicant_info(Username varchar(50),First_name varchar(40),Last_name varchar(40),mobileno varchar(12),city varchar(10),account_no varchar(14),account_balance varchar(10))")
#Username,First_name,Last_name,mobileno,city,account_no,account_balance,created
#mycursor.execute("ALTER TABLE Login_authenticate MODIFY COLUMN password varchar(255)")

def shownow():
	mycursor.execute("SELECT password FROM Login_authenticate WHERE Username='prateekaphale@gmail.com'")
	x = mycursor.fetchone()
	print(x)
shownow()
#checkinfo()
def kill():
	if situation == "Its already exist":
		errorbox.destroy()
		show_that_frame(homescreen)
		usernamestoree.set('')
		passwordstoree.set('')
	elif situation == "Its done":
		#successwin.destroy()
		show_that_frame(loginscreen)
	elif situation =="Logged in":
		#logwin.after(200,lambda:logwin.destroy())
		show_that_frame(loggedinscreen)
	elif situation =="incorrect username or password":
		errorwin.destroy()
		show_that_frame(loginscreen)
		usernamestoree.set('')
		passwordstoree.set('')

def showloggedin():
	global logwin
	global situation
	situation = "Logged in"
	logwin = Toplevel(window)
	logwin.geometry("400x400")
	logwin.title("Succesful entry")
	Label(logwin,text=f"Logged in Succesfully",fg = "green").pack()
	#Button(logwin,text = "Ok", command = kill,width=10).pack()
	logwin.after(900,lambda:logwin.destroy())
	kill()
def showsuccess():
	global successwin
	global situation
	situation = "Its done"
	successwin = Toplevel(window)
	successwin.geometry("400x400")
	successwin.title("Succesful entry")
	Label(successwin,text="Your form details are submitted Succesfully",fg = "green").pack()
	successwin.after(900,lambda:successwin.destroy())
	kill()
	#Button(successwin,text = "Ok", command = kill,width=10).pack()
def showerror():
	global situation
	situation = "incorrect username or password"
	global errorwin
	errorwin = Toplevel(window)
	errorwin.geometry("300x300")
	errorwin.title("Warning!")
	Label(errorwin,text = "Username or password incorrect",fg ="red").pack()
	Button(errorwin,text="OK",command = kill,width=10).pack()
def already_exist_popup():
	global errorbox
	global situation
	situation = "Its already exist"
	errorbox = Toplevel(window)
	errorbox.geometry("200x200")
	errorbox.title("already exist")
	Label(errorbox,text="User already exist!",fg="red").pack()
	Button(errorbox,text="Ok",command=kill,width=10).pack()

#------------------------------------------------------Security-------------------	
def hashing(password):
	global hashed
	salt = bcrypt.gensalt()
	hashed = bcrypt.hashpw(bytes(password,'utf-8'),salt)
	return hashed
def checkhash(password):
	return bcrypt.checkpw(bytes(password,'utf-8'),hashed)
#_-----------------------------------------------------------Done----------------------


def checkinfo():

	username_temp = usernamestoree.get()
	password_temp = passwordstoree.get().encode('utf-8')
	if username_temp == "":
		showerror()
	elif password_temp == "":
		showerror()
	else:
		try:
			mycursor.execute("SELECT password FROM Login_authenticate WHERE Username = %s",[(username_temp)])
			results = mycursor.fetchone()
			if bcrypt.checkpw(password_temp,bytes(results[0],'utf-8')):
				showloggedin()
			else:
				showerror()
		except:
			showerror()

def already_exist():
	newuserid = newuserstore.get()
	newuserid = newuserstore.get()
	mycursor.execute("SELECT Username FROM Login_authenticate WHERE Username=%s",[(newuserid),])
	results = mycursor.fetchall()
	if results:
		return True
	else:
		return False

def insert_all_info():
	newuserid = newuserstore.get()
	newpassword = newpasswordstore.get()
	firstnamee = firstnamestore.get() 
	lastname = lastnamestore.get()
	mobilenum = mobilenostore.get()
	city = citystore.get()
	accountnumber = accountnumberstore.get()
	openbalance = openingbalance.get()
	mycursor.execute("INSERT INTO Login_authenticate(Username,password) VALUES (%s,%s)",(newuserid,hashing(newpassword)))#,datetime.now(),gendertemp))
	db.commit()
	mycursor.execute("INSERT INTO Applicant_info(Username,First_name,Last_name,mobileno,city,account_no,account_balance,created) VALUES(%s,%s,%s,%s,%s,%s,%s,%s)",(newuserid,firstnamee,lastname,mobilenum,city,accountnumber,openbalance,datetime.now()))
	db.commit()
	showsuccess()

#-----------------------------def loggedin():-----------------------------------------------------------------
def checkcurrent():
	def kill1():
		#popup.destroy()

		dis.destroy()
		show_that_frame(loggedinscreen)
	def checkhere():
		userr = usernamestoree.get()
		mycursor.execute("SELECT password FROM Login_authenticate WHERE Username=%s",[(userr)])
		result = mycursor.fetchone()
		print(result[0])
		passs = passwordd.get().encode('utf-8')
		#hashing(passs)
		if bcrypt.checkpw(passs,bytes(result[0],'utf-8')):
			popup.destroy()
			displayhere()
		else:
			print("no")
		#if result:
		#	displayhere()
		#else:
		#	print("invalid")
	def displayhere():
		global dis
		Username = usernamestoree.get()
		mycursor.execute("SELECT account_balance FROM Applicant_info WHERE Username=%s",[(Username)])
		results = mycursor.fetchone()
		dis = Toplevel(window)
		dis.geometry("300x300")
		Label(dis,text="your account has"+results[0]).pack()
		Button(dis,text="ok",command=kill1).pack()
		#show_that_frame(dis)
	

	passwordd = StringVar()
	popup = Toplevel(window)
	#popup.pack()
	popup.geometry("400x400")
	popup.title("Authntication")
	Label(popup,text="Enter password").pack()
	Entry(popup,textvariable=passwordd,show="*").pack()
	#passworddd = passwordd.get()
	Button(popup,text="submit",command=checkhere).pack()
#-----------------------------------------------------------------Deposit amount--------------------------------------------------------------
def depositamount():
	def checkaccount():
		acc = accountnumber.get()
		deposit = depositamountt.get()
		mycursor.execute("SELECT account_no FROM Applicant_info WHERE account_no =%s",[(acc)])
		result = mycursor.fetchone()
		if result:
			sumitup = result[0][0] + deposit
			mycursor.execute("UPDATE Applicant_info set account_balance = %s WHERE account_no =%s",[(sumitup),(acc)])
			popup2 = Toplevel(window)
			popup2.title("Succesful")
			Label(popup2,text="Amount deposited succesfully.",font=("",8,"bold"),fg="green").pack()
			popup2.after(900,lambda:popup2.destroy())
		else:
			popup3 = Toplevel(window)
			popup3.geometry("300x300")
			popup3.title("Warning!")
			Label(popup3,text="Incorrect account number entered!",fg="red",font=("",8,"bold")).pack()
			Button(popup3,text="Ok",command=popup3.destroy()).pack()

	accountnumber = StringVar()
	depositamountt = StringVar()
	popup = Toplevel(window)
	popup.geometry("400x400")
	popup.title("Deposit amount")
	Label(popup,text="Enter account number:").pack()
	Entry(popup,textvariable = accountnumber).pack()
	Label(popup,text="Enter amount:").pack()
	Entry(popup,textvariable = depositamountt).pack()
	Button(popup,text="submit",command = checkaccount).pack()

#---------------------------------------------------------------------------------Loggedin ends here----------------------------

#def insertt():
#	loginnew = table 
#	password new = table
#	first name = tabel2
#	last name = tabel2

def registeratfirst():
	already_exist()
	if already_exist():
		already_exist_popup()
	else:
		insertt()
		print("new one")
#def createaccount():
#	if user present in database:
#		update city,created,mobile number,openingbalance where username = loginuser:
#		popup()
#		show = Your accountnumber is 

#-------------------------------------
def register():
	already_exist()
	if already_exist():
		already_exist_popup()
		print("already_exist")
	else:
		insert_all_info()
		print("new one")
		
		

window = Tk()

window.geometry("1000x1000")
window.rowconfigure(0, weight=1)
window.columnconfigure(0, weight=1)
homescreen = Frame(window)
loginscreen = Frame(window)
registerscreen = Frame(window)
loggedinscreen = Frame(window)
showmoneyscreen = Frame(window,width = 00,height=100)
for frame in (homescreen,loginscreen,registerscreen,loggedinscreen,showmoneyscreen):
	frame.grid(row = 0, column = 0,sticky='nsew')
def show_that_frame(frame):
	frame.tkraise()
show_that_frame(homescreen)
def logout():
	def dis():
		popup1.destroy()
	def showthat():
		popup1.destroy()
		show_that_frame(loginscreen)
		usernamestoree.set('')
		passwordstoree.set('')
	popup1 = Toplevel(window)
	popup1.geometry("300x300")
	popup1.title("Logout window")
	Label(popup1,text="Are you sure?").pack()
	#passworddd = passwordd.get()
	Button(popup1,text="yes",command=showthat).pack()
	Button(popup1,text="no",command = dis).pack()
	

#------------------------------------Now home screen-------------
window.title("Banking Application")
homelabel = Label(homescreen,text = " Welcome to the portal",font=("",15))
homelabel.pack()
homelogin = Button(homescreen,fg="blue",text="Login",font=("",12),width=14,height = 3,command=lambda: show_that_frame(loginscreen))
homelogin.place(x=400,y=230)
homeregister = Button(homescreen,fg="blue",text="Register",font=("",12),width=14,height=3,command = lambda: show_that_frame(registerscreen))
homeregister.place(x=400,y=300)
#----------------------------------------loginscreen-----------------------------

#loginlabel = Label(loginscreen,text="you are on a login page",font=("",15))
#loginlabel.pack()
login_back_button = Button(loginscreen,text="go to home",font=("",15),width=9,command=lambda: show_that_frame(homescreen))
login_back_button.pack()


label1 = Label(loginscreen, text = "Username",font=("",12,"bold"))
label1.place(x=20,y=80)
label2 = Label(loginscreen,text = "Password",font=("",12,"bold"))
label2.place(x=20,y=150)
usernamestoree = StringVar()
passwordstoree = StringVar()
entry1 = Entry(loginscreen,textvariable = usernamestoree)
entry1.place(x=20,y=110)
entry2 = Entry(loginscreen,textvariable = passwordstoree,show = "*")
entry2.place(x=20,y=180)
Button1 = Button(loginscreen,text = "Submit",command = checkinfo,width = 60)
Button1.place(x = 20, y = 300)
#--------------------------------------------------------registrationscreen---------------
Labelre1 = Label(registerscreen,text = " Registration form - Enter details",font=("",15))
Labelre1.pack()
Labelre1 = Label(registerscreen, text = "Firstname ",font=("",12,"bold"))
Labelre1.place(x=20,y=80)
Labelre2 = Label(registerscreen,text = "Lastname:",font=("",12,"bold"))
Labelre2.place(x=390,y=80)
Labelre3 = Label(registerscreen,text = "Mobile no.:",font=("",12,"bold")).place(x = 750,y=80)
Labelre4=Label(registerscreen,text = " City:",font=("",12,"bold")).place(x=20,y=160)
Labelre5 = Label(registerscreen,text = "Enter username:",font=("",12,"bold")).place(x = 390,y=160)
Labelre6 = Label(registerscreen,text = "Enter password:",font=("",12,"bold")).place(x = 750,y=160)
Labelre7 = Label(registerscreen,text = "Enter account No:",font=("",12,"bold")).place(x=20,y=240)
Labelre8 = Label(registerscreen,text = "Enter opening balance:",font=("",12,"bold")).place(x=390,y=240)
newpasswordstore = StringVar()
firstnamestore = StringVar()
lastnamestore = StringVar()
mobilenostore = StringVar()
newuserstore = StringVar()
citystore = StringVar()

accountnumberstore = StringVar()
openingbalance= StringVar()
entry1 = Entry(registerscreen,textvariable = firstnamestore).place(x=20,y=110)
entry2 = Entry(registerscreen,textvariable = lastnamestore).place(x=390,y=110)
entry3 = Entry(registerscreen,textvariable = mobilenostore).place(x=750,y=110)
entry4 = Entry(registerscreen,textvariable = citystore).place(x=20,y=190)
entry5 = Entry(registerscreen,textvariable = newuserstore).place(x=390,y=190)
entry6 = Entry(registerscreen,textvariable = newpasswordstore,show = "*").place(x=750,y=190)
entry7 = Entry(registerscreen,textvariable = accountnumberstore).place(x=20,y=270)
entry8 = Entry(registerscreen,textvariable = openingbalance).place(x=390,y=270)

submit_button = Button(registerscreen,text="Submit",fg="green",font=("",12,"bold"),width=12,height=3,command=register).place(x=300,y=350)
return_button = Button(registerscreen,text="homescreen",fg="green",font=("",12,"bold"),width=20,height=1,command=lambda:show_that_frame(homescreen)).place(x=470,y=350)

#-------------------------------------loggedin screen-------------
Logged1 = Label(loggedinscreen,text = "Welcome to our netbanking page",fg = "red",bg="yellow",font=("",15)).pack()
butt1 = Button(loggedinscreen,text = " Show current balance ",fg="green",font=("",9,"bold"),width = 17,height=3,command=checkcurrent).place(x=40,y=170)
butt2 = Button(loggedinscreen,text = "Logout",bg="pink",font=("",6,"bold"),width=9,command=logout).pack()
butt3 = Button(loggedinscreen,text = " Deposit amount ",fg="green",font=("",9,"bold"),width = 17,height=3,command=depositamount).place(x=290,y=170)
butt4 = Button(loggedinscreen,text = " Withdraw amount ",fg="green",font=("",9,"bold"),width = 17,height=3).place(x=540,y=170)
butt5 = Button(loggedinscreen,text = " Account details ",fg="green",font=("",9,"bold"),width = 17,height=3).place(x=780,y=170)
butt6 = Button(loggedinscreen,text = " Close account ",fg="green",font=("",9,"bold"),width = 17,height=3).place(x=40,y=310)
butt7 = Button(loggedinscreen,text = " Close account ",fg="green",font=("",9,"bold"),width = 17,height=3).place(x=40,y=310)



#show_that_frame(loggedinscreen)

window.mainloop()
