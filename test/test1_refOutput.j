.class public Foo
.super java/lang/Object


;
; standard constructor
;
.method public <init>()V
  aload_0
  invokenonvirtual java/lang/Object/<init>()V
  return
.end method


;
; main method
;
.method public static main([Ljava/lang/String;)V
  invokestatic Foo/program()V
  return
.end method


;
; println method
;
.method public static println(I)V
  .limit stack 2
  getstatic java/lang/System/out Ljava/io/PrintStream;
  iload_0
  invokevirtual java/io/PrintStream/println(I)V
  return
.end method


;
; method
;
.method public static program()V
  .limit locals 10
  .limit stack 10
  ldc 1
  istore 0
  ldc 2
  istore 1
  iload 0
  iload 1
  invokestatic Foo/myAdd(II)I
  istore 2
  iload 2
  invokestatic Foo/println(I)V
  return
.end method

;
; method
;
.method public static myAdd(II)I
  .limit locals 10
  .limit stack 10
  iload 0
  iload 1
  iadd
  ireturn
.end method
