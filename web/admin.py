from flask import Blueprint,render_template,request,redirect,url_for,session,flash
from database import*

admin=Blueprint("admin",__name__)

@admin.route("/admin_home")
def admin_home():
	if not session.get("lid") is None: 
		return render_template("admin_home.html")
	else:
		return redirect(url_for("public.login"))



@admin.route("/admin_doctor_registration",methods=['get','post'])
def admin_doctor_registration():
	if not session.get("lid") is None:
		data={}

		q="select * from department"
		res=select(q)
		data['de']=res 


		q="select *,concat(first_name,' ',last_name) as fullname from doctors inner join login using(login_id) inner join department using(department_id)"
		data['view']=select(q)
		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None
		if action=='update':
			q="select * from doctors where login_id='%s'"%(id)
			data['view_update']=select(q)

		if 'update_submit' in request.form:
			fname=request.form['fname']
			lname=request.form['lname']
			phone=request.form['phone']
			email=request.form['email']
			place=request.form['place']
			qlf=request.form['qlf']
			q="update login  set username='%s',password='%s' where login_id='%s'"%(email,phone,id)
			update(q)
			q="update doctors set `first_name`='%s',`last_name`='%s',`phone`='%s',`email`='%s',`place`='%s',`qualification`='%s' where login_id='%s'"%(fname,lname,phone,email,place,qlf,id)
			update(q)
			flash("Changed")
			return redirect(url_for("admin.admin_doctor_registration"))

		if action=='delete':
			q="delete from login where login_id='%s'"%(id)
			delete(q)
			q="delete from doctors where login_id='%s'"%(id)
			delete(q)
			flash("Deleted")
			return redirect(url_for("admin.admin_doctor_registration"))

		if 'submit' in request.form:
			fname=request.form['fname']
			lname=request.form['lname']
			phone=request.form['phone']
			email=request.form['email']
			place=request.form['place']
			dept=request.form['dept']
			qlf=request.form['qlf']
			q="INSERT INTO login VALUES(NULL,'%s','%s','doctor')"%(email,phone)
			ids=insert(q)
			q="INSERT INTO doctors(`login_id`,`department_id`,`first_name`,`last_name`,`phone`,`email`,`place`,`qualification`) VALUES('%s','%s','%s','%s','%s','%s','%s','%s')"%(ids,dept,fname,lname,phone,email,place,qlf)
			insert(q)
			flash("Doctor Registered")
			return redirect(url_for("admin.admin_doctor_registration"))
		return render_template("admin_doctor_registration.html",data=data)
	else:
		return redirect(url_for("public.login"))






@admin.route("/admin_manage_department",methods=['get','post'])
def admin_manage_department():
	if not session.get("lid") is None:
		data={}
		q="select * from department"
		data['view']=select(q)


		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None
		if action=='update':
			q="select * from department where department_id='%s'"%(id)
			data['view_update']=select(q)

		if 'update_submit' in request.form:
			dept=request.form['dept']
			q="update department  set department='%s' where department_id='%s'"%(dept,id)
			update(q)
			flash("Changed")
			return redirect(url_for("admin.admin_manage_department"))

		if action=='delete':
			q="delete from department where department_id='%s'"%(id)
			delete(q)
			flash("Deleted")
			return redirect(url_for("admin.admin_manage_department"))

		if 'submit' in request.form:
			dept=request.form['dept']

			q="INSERT INTO department VALUES(NULL,'%s')"%(dept)
			ids=insert(q)
			flash("Department Added")
			return redirect(url_for("admin.admin_manage_department"))
		return render_template("admin_manage_department.html",data=data)
	else:
		return redirect(url_for("public.login"))




@admin.route("/admin_laboratory_registration",methods=['get','post'])
def admin_laboratory_registration():
	if not session.get("lid") is None:
		data={}
		q="select * from laboratory inner join login using(login_id)"
		data['view']=select(q)
		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None
		if action=='update':
			q="select * from laboratory where login_id='%s'"%(id)
			data['view_update']=select(q)
		if 'update_submit' in request.form:
			lname=request.form['pname']
			phone=request.form['phone']
			email=request.form['email']
			q="update login set username='%s',password='%s' where login_id='%s'"%(email,phone,id)
			update(q)
			q="update laboratory set lab_name='%s',phone='%s',email='%s' where login_id='%s'"%(lname,phone,email,id)
			update(q)
			flash("Changes Saved")
			return redirect(url_for("admin.admin_laboratory_registration"))

		if action=='delete':
			q="delete from login where login_id='%s'"%(id)
			delete(q)
			q="delete from laboratory where login_id='%s'"%(id)
			delete(q)
			flash("Deleted")
			return redirect(url_for("admin.admin_laboratory_registration"))

		if 'submit' in request.form:
			lname=request.form['pname']
			phone=request.form['phone']
			email=request.form['email']
			q="INSERT INTO login VALUES(NULL,'%s','%s','lab')"%(email,phone)
			iid=insert(q)
			q="INSERT INTO laboratory VALUES(NULL,'%s','%s','%s','%s')"%(iid,lname,phone,email)
			insert(q)
			flash("Laboratory Registered")
			return redirect(url_for("admin.admin_laboratory_registration"))
		return render_template("admin_laboratory_registration.html",data=data)
	else:
		return redirect(url_for("public.login"))



@admin.route("/admin_pharmacy_registration",methods=['get','post'])
def admin_pharmacy_registration():
	if not session.get("lid") is None:
		data={}
		q="select * from pharmacy inner join login using(login_id)"
		data['view']=select(q)
		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
		else:
			action=None
		if action=='update':
			q="select * from pharmacy where login_id='%s'"%(id)
			data['view_update']=select(q)
		if 'update_submit' in request.form:
			lname=request.form['pname']
			phone=request.form['phone']
			email=request.form['email']
			q="update login set username='%s',password='%s' where login_id='%s'"%(email,phone,id)
			update(q)
			q="update pharmacy set pharmacy_name='%s',phone='%s',email='%s' where login_id='%s'"%(lname,phone,email,id)
			update(q)
			flash("Changes Saved")
			return redirect(url_for("admin.admin_pharmacy_registration"))

		if action=='delete':
			q="delete from login where login_id='%s'"%(id)
			delete(q)
			q="delete from pharmacy where login_id='%s'"%(id)
			delete(q)
			flash("Deleted")
			return redirect(url_for("admin.admin_pharmacy_registration"))

		if 'submit' in request.form:
			pname=request.form['pname']
			email=request.form['email']
			phone=request.form['phone']
			q="INSERT INTO login VALUES(NULL,'%s','%s','pharmacy')"%(email,phone)
			id=insert(q)
			q="INSERT INTO pharmacy VALUES(NULL,'%s','%s','%s','%s')"%(id,pname,email,phone)
			insert(q)
			flash("Pharmacy Registered")
			return redirect(url_for("admin.admin_pharmacy_registration"))
		return render_template("admin_pharmacy_registration.html",data=data)
	else:
		return redirect(url_for("public.login"))




@admin.route("/admin_schedule_doctor_consulting_times",methods=['get','post'])
def admin_schedule_doctor_consulting_times():
	if not session.get("lid") is None:
		data={}
		if 'ids' in request.args:
			ids=request.args['ids']
			q="delete from SCHEDULE where schedule_id='%s'"%(ids)
			delete(q)
			flash("Removed")
			return redirect(url_for("admin.admin_schedule_doctor_consulting_times"))
		if 'submit' in request.form:
			did=request.form['did']
			stime=request.form['stime']
			etime=request.form['etime']
			date=request.form['date']
			fee=request.form['fee']
			q="INSERT INTO SCHEDULE (doctor_id,`start_time`,`end_time`,`date`,`fee_amount`) VALUES('%s','%s','%s','%s','%s')"%(did,stime,etime,date,fee)
			insert(q)
			flash("Schedule committed")
			return redirect(url_for("admin.admin_schedule_doctor_consulting_times"))
		q="select *,concat(first_name,' ',last_name) as doctor_name from doctors"
		data['did']=select(q)
		q="select *,concat(first_name,' ',last_name) as doctor_name from doctors inner join schedule using(doctor_id)"
		data['view']=select(q)
		return render_template("admin_schedule_doctor_consulting_times.html",data=data)
	else:
		return redirect(url_for("public.login"))



@admin.route("/admin_view_patients")
def admin_view_patients():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,concat(first_name,' ',last_name) as name FROM patients"
		data['view']=select(q)
		return render_template("admin_view_patients.html",data=data)
	else:
		return redirect(url_for("public.login"))




@admin.route("/admin_view_payment_details")
def admin_view_payment_details():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,CONCAT(first_name,' ',last_name) AS NAME FROM `payments` INNER JOIN `bookings` USING(book_id) INNER JOIN `patients` USING(patient_id)"
		#q="SELECT *,CONCAT(first_name,' ',last_name) AS NAME FROM payments INNER JOIN bookings USING(book_id) INNER JOIN users USING(user_id)"
		data['view']=select(q)
		return render_template("admin_view_payment_details.html",data=data)
	else:
		return redirect(url_for("public.login"))