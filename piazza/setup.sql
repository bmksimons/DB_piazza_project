create table PiazzaUser(
	UserID integer not null,
	Email varchar(30),
	UserPassword varchar(30),
	Name varchar(30),
	Type varchar(20),
	constraint PiazzaUser_pk primary key (UserID));

create table Course(
	CourseID integer not null,
	CourseName varchar(30),
	Term varchar(10),
	AllowAnonymous Boolean,
	constraint Course_pk primary key (CourseID));

create table Member(
	UserID integer not null,
	CourseID integer not null,
	constraint Member_pk primary key (UserID, CourseID),
	constraint Member_fk1 foreign key (UserID) references PiazzaUser (UserID)
		on update cascade
        	on delete cascade,
    	constraint Member_fk2 foreign key (CourseID) references Course (CourseID)
		on update cascade
        	on delete cascade);

create table Managing(
	UserID integer not null,
	CourseID integer not null,
	constraint Managing_pk primary key (UserID, CourseID),
	constraint Managing_fk1 foreign key (UserID) references PiazzaUser (UserID)
		on update cascade
       	 	on delete cascade,
    	constraint Managing_fk2 foreign key (CourseID) references Course (CourseID)
		on update cascade
       	 	on delete cascade);

create table Folder(
	CourseID integer not null,
	FolderID integer not null,
	FolderName varchar(30),
	constraint Folder_pk primary key (CourseID, FolderID),
	constraint Folder_fk foreign key (CourseID) references Course (CourseID)
		on update cascade
		on delete cascade);

create table Tag(
	TagID integer not null,
	Title varchar(20),
	constraint Tag_pk primary key (TagID));

create table Thread(
	ThreadID integer not null,
	CourseID integer not null,
	FolderID integer not null,
	TagID integer,
	constraint Thread_pk primary key (ThreadID),
	constraint Thread_fk1 foreign key (CourseID) references Course (CourseID)
		on update cascade
        	on delete cascade,
    	constraint Thread_fk2 foreign key (CourseID, FolderID) references Folder (CourseID, FolderID)
		on update cascade
        	on delete cascade,
	constraint Thread_fk3 foreign key (TagID) references Tag (TagID)
		on update cascade
        	on delete cascade);

create table Post(
	PostID integer not null,
	UserID integer not null,
	Title varchar(20),
	Description varchar(500),
	ColorCode varchar(10),
	Date Date,
	Time Time,
	ThreadID integer not null,
	ReplyToID integer,
	constraint Post_pk primary key (PostID),
	constraint Post_fk1 foreign key (UserID) references PiazzaUser (UserID)
		on update cascade
        	on delete cascade,
    	constraint Post_fk2 foreign key (ThreadID) references Thread (ThreadID)
		on update cascade
        	on delete cascade,
	constraint Post_fk3 foreign key (ReplyToID) references Post (PostID)
		on update cascade
        	on delete cascade);


create table GoodComment(
	UserID integer not null,
	PostID integer not null,
	constraint GoodComment_pk primary key (UserID, PostID),
	constraint GoodComment_fk1 foreign key (UserID) references PiazzaUser (UserID)
		on update cascade
        	on delete cascade,
    	constraint GoodComment_fk2 foreign key (PostID) references Post (PostID)
		on update cascade
        	on delete cascade);

create table HasRead(
	UserID integer not null,
	ThreadID integer not null,
	constraint HasRead_pk primary key (UserID, ThreadID),
	constraint HasRead_fk1 foreign key (UserID) references PiazzaUser (UserID)
		on update cascade
        	on delete cascade,
    	constraint HasRead_fk2 foreign key (ThreadID) references Thread (ThreadID)
		on update cascade
        	on delete cascade);

create table ReferencingThread(
	ThreadID integer not null,
	PostID integer not null,
	constraint ReferencingThread_pk primary key (ThreadID, PostID),
	constraint ReferencingThread_fk1 foreign key (ThreadID) references Thread (ThreadID)
		on update cascade
        	on delete cascade,
    	constraint ReferencingThread_fk2 foreign key (PostID) references Post (PostID)
		on update cascade
        	on delete cascade);

insert into Course values(1, "Database", "Vaar", False);
insert into Folder values(1, 1, "Exam");
insert into Tag values(1, "Question"); 





	
